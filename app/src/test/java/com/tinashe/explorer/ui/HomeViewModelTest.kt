package com.tinashe.explorer.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.tinashe.explorer.sdk.data.model.Entity
import com.tinashe.explorer.sdk.data.model.EntityType
import com.tinashe.explorer.sdk.data.repository.ExplorerRepository
import com.tinashe.explorer.utils.RxSchedulers
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks


class HomeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: ExplorerRepository
    @Mock
    lateinit var mockRxSchedulers: RxSchedulers

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        initMocks(this)

        `when`(mockRxSchedulers.main).thenReturn(Schedulers.trampoline())
        `when`(mockRepository.listCities()).thenReturn(Observable.just(cities))

        viewModel = HomeViewModel(mockRepository, mockRxSchedulers)
    }

    @Test
    fun list_cities_on_init() {
        verify(mockRepository).listCities()
    }

    @Test
    fun entity_selected_type_city_fetch_list_of_malls_shops() {
        `when`(mockRepository.listMalls(city.id)).thenReturn(Maybe.just(emptyList()))
        `when`(mockRepository.listShopsInCity(city.id)).thenReturn(Maybe.just(emptyList()))

        viewModel.entitySelected(city)

        verify(mockRepository).listMalls(city.id)
        verify(mockRepository).listShopsInCity(city.id)
    }

    @Test
    fun entity_selected_type_mall_fetch_list_of_shops() {
        val mall = Entity(2, "Century City", EntityType.MALL)

        `when`(mockRepository.listShops(mall.id)).thenReturn(Maybe.just(emptyList()))

        viewModel.entitySelected(mall)

        verify(mockRepository).listShops(mall.id)
    }

    @Test
    fun navigate_back_returns_true_when_level_is_null() {
        assertTrue(viewModel.navigateBack())
    }

    @Test
    fun navigate_back_returns_true_when_level_is_city() {
        viewModel.level.value = EntityType.CITY

        assertTrue(viewModel.navigateBack())
    }

    @Test
    fun navigate_back_returns_false_when_level_is_mall() {
        viewModel.level.value = EntityType.MALL

        assertFalse(viewModel.navigateBack())
    }

    @Test
    fun navigate_back_returns_false_when_level_is_shop() {
        viewModel.level.value = EntityType.SHOP

        assertFalse(viewModel.navigateBack())
    }

    companion object {
        private val city = Entity(1, "Cape Town", EntityType.CITY)

        private val cities = arrayListOf(city, city, city)
    }
}