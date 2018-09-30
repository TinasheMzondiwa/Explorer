package com.tinashe.explorer.sdk.data.repository

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.tinashe.explorer.sdk.data.db.ExplorerDatabase
import com.tinashe.explorer.sdk.data.model.City
import com.tinashe.explorer.sdk.data.model.Entity
import com.tinashe.explorer.sdk.data.model.EntityType.*
import com.tinashe.explorer.sdk.data.network.ExplorerApi
import com.tinashe.explorer.sdk.data.network.RestClient
import com.tinashe.explorer.sdk.exceptions.BaseExplorerSdkException
import com.tinashe.explorer.sdk.exceptions.ConnectivityException
import com.tinashe.explorer.sdk.utils.NetworkHelper
import com.tinashe.explorer.sdk.utils.RxSchedulers
import io.reactivex.Maybe
import io.reactivex.Observable

internal class ExplorerRepositoryImpl : ExplorerRepository {

    private val context: Context
    private val api: ExplorerApi
    private val database: ExplorerDatabase
    private val networkHelper: NetworkHelper
    private val rxSchedulers: RxSchedulers

    constructor(context: Context) : this(context, RestClient.createService(ExplorerApi::class.java),
            ExplorerDatabase.create(context), NetworkHelper(), RxSchedulers())

    @VisibleForTesting
    constructor(context: Context, api: ExplorerApi, database: ExplorerDatabase, networkHelper: NetworkHelper, rxSchedulers: RxSchedulers) {
        this.context = context
        this.api = api
        this.database = database
        this.networkHelper = networkHelper
        this.rxSchedulers = rxSchedulers
    }

    override fun listCities(): Observable<List<Entity>> {
        return if (networkHelper.hasConnection(context)) {
            api.getAllCities()
                    .subscribeOn(rxSchedulers.network)
                    .doOnNext {
                        if (it.isSuccessful) {
                            it.body()?.cities?.let { cities ->
                                cacheResources(cities)
                            }
                        }
                    }
                    .flatMap {

                        if (it.isSuccessful) {

                            it.body()?.cities?.forEach { city -> city.type = CITY }

                            Observable.just(it.body()?.cities ?: emptyList())

                        } else {
                            Observable.error(BaseExplorerSdkException())
                        }
                    }
        } else {
            database.citiesDao().listAll().toObservable()
                    .subscribeOn(rxSchedulers.database)
                    .flatMap {
                        if (it.isEmpty()) {
                            Observable.error(ConnectivityException())
                        } else {
                            it.forEach { city -> city.type = CITY }
                            Observable.just(it)
                        }
                    }
        }
    }

    internal fun cacheResources(cities: List<City>) {
        rxSchedulers.database.apply {
            cities.forEach { city ->
                database.citiesDao().insert(city)

                city.malls.forEach { mall ->
                    mall.city = city.id
                    database.mallsDao().insert(mall)

                    mall.shops.forEach { shop ->
                        shop.city = city.id
                        shop.mall = mall.id
                        database.shopsDao().insert(shop)
                    }
                }
            }
        }
    }

    override fun findCity(id: Int): Maybe<Entity> {
        return database.citiesDao().findCity(id)
                .subscribeOn(rxSchedulers.database)
                .flatMap { Maybe.just(Entity(it.id, it.name, CITY)) }
    }

    override fun listMalls(cityId: Int): Maybe<List<Entity>> {
        return database.mallsDao().listAll(cityId)
                .subscribeOn(rxSchedulers.database)
                .flatMap { malls -> Maybe.just(malls.map { Entity(it.id, it.name, MALL) }) }
    }

    override fun findMall(id: Int, cityId: Int): Maybe<Entity> {
        return database.mallsDao().findMall(id, cityId)
                .subscribeOn(rxSchedulers.database)
                .flatMap { Maybe.just(Entity(it.id, it.name, MALL)) }
    }

    override fun listShops(mallId: Int): Maybe<List<Entity>> {
        return database.shopsDao().listAllInMall(mallId)
                .subscribeOn(rxSchedulers.database)
                .flatMap { shops -> Maybe.just(shops.map { Entity(it.id, it.name, SHOP) }) }
    }

    override fun listShopsInCity(cityId: Int): Maybe<List<Entity>> {
        return database.shopsDao().listAll(cityId)
                .subscribeOn(rxSchedulers.database)
                .flatMap { shops -> Maybe.just(shops.map { Entity(it.id, it.name, SHOP) }) }
    }

    override fun findShop(id: Int, mallId: Int): Maybe<Entity> {
        return database.shopsDao().findInMall(id, mallId)
                .subscribeOn(rxSchedulers.database)
                .flatMap { Maybe.just(Entity(it.id, it.name, SHOP)) }
    }
}