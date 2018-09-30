package com.tinashe.explorer.ui

import android.arch.lifecycle.MutableLiveData
import com.tinashe.explorer.R
import com.tinashe.explorer.sdk.data.model.Entity
import com.tinashe.explorer.sdk.data.model.EntityType
import com.tinashe.explorer.sdk.data.model.EntityType.*
import com.tinashe.explorer.sdk.data.repository.ExplorerRepository
import com.tinashe.explorer.sdk.exceptions.ConnectivityException
import com.tinashe.explorer.ui.base.RxAwareViewModel
import com.tinashe.explorer.ui.base.SingleLiveEvent
import com.tinashe.explorer.utils.RxSchedulers
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: ExplorerRepository,
                                        private val rxSchedulers: RxSchedulers) : RxAwareViewModel() {

    var displayTitle = MutableLiveData<String>()
    var entityList = MutableLiveData<ArrayList<Entity>>()
    var level = MutableLiveData<EntityType>()
    var errorRes = SingleLiveEvent<Int>()

    private var stack = SingleLiveEvent<ArrayList<Entity>>()

    init {
        listCities()
        stack.value = arrayListOf()
    }

    private fun listCities() {
        val disposable = repository.listCities()
                .observeOn(rxSchedulers.main)
                .subscribe({
                    entityList.postValue(ArrayList(it))

                    level.postValue(CITY)
                    displayTitle.postValue(null)
                }, {
                    Timber.e(it)

                    if (it is ConnectivityException) {
                        errorRes.postValue(R.string.error_connection)
                    } else {
                        errorRes.postValue(R.string.error_default)
                    }
                })

        disposables.add(disposable)
    }

    fun entitySelected(entity: Entity) {

        when (entity.type) {

            CITY -> {
                displayTitle.postValue(entity.name)

                val disposable = Maybe.zip(repository.listMalls(entity.id),
                        repository.listShopsInCity(entity.id),
                        BiFunction<List<Entity>, List<Entity>, ArrayList<Entity>> { malls, shops ->
                            ArrayList(malls).apply {
                                addAll(shops)
                            }
                        })
                        .observeOn(rxSchedulers.main)
                        .doAfterSuccess {
                            level.postValue(MALL)

                            if (stack.value?.contains(entity) == false) {
                                stack.value?.add(entity)
                            }
                        }
                        .subscribe({
                            entityList.postValue(it)

                        }, {
                            Timber.e(it)
                            errorRes.postValue(R.string.error_default)
                        })

                disposables.add(disposable)
            }
            MALL -> {
                displayTitle.postValue(entity.name)

                val disposable = repository.listShops(entity.id)
                        .observeOn(rxSchedulers.main)
                        .doAfterSuccess {
                            level.postValue(SHOP)

                            if (stack.value?.contains(entity) == false) {
                                stack.value?.add(entity)
                            }
                        }
                        .subscribe({
                            entityList.postValue(ArrayList(it))

                        }, {
                            Timber.e(it)
                            errorRes.postValue(R.string.error_default)
                        })
                disposables.add(disposable)
            }
            SHOP -> {
                // no ops
            }
        }
    }

    fun navigateBack(): Boolean {
        return when (level.value) {

            CITY, null -> true
            MALL -> {
                level.postValue(CITY)
                popStack()
                false
            }
            SHOP -> {
                level.postValue(MALL)
                popStack()
                false
            }
        }
    }

    private fun popStack() {
        var list = stack.value?.toList() ?: return
        if (list.isEmpty()) {
            return
        }

        list = list.dropLast(1)

        if (list.isEmpty()) {
            listCities()
        } else {
            entitySelected(list.last())
        }

        stack.value = ArrayList(list)

    }
}