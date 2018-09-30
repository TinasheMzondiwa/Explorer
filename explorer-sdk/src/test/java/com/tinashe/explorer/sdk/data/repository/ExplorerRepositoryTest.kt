package com.tinashe.explorer.sdk.data.repository

import android.content.Context
import com.tinashe.explorer.sdk.data.db.ExplorerDatabase
import com.tinashe.explorer.sdk.data.db.dao.CitiesDao
import com.tinashe.explorer.sdk.data.db.dao.MallsDao
import com.tinashe.explorer.sdk.data.db.dao.ShopsDao
import com.tinashe.explorer.sdk.data.model.City
import com.tinashe.explorer.sdk.data.model.EntityType.*
import com.tinashe.explorer.sdk.data.model.Mall
import com.tinashe.explorer.sdk.data.model.Shop
import com.tinashe.explorer.sdk.data.model.response.CitiesResponse
import com.tinashe.explorer.sdk.data.network.ExplorerApi
import com.tinashe.explorer.sdk.utils.NetworkHelper
import com.tinashe.explorer.sdk.utils.RxSchedulers
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Response

internal class ExplorerRepositoryTest {

    @Mock
    lateinit var mockContext: Context
    @Mock
    lateinit var mockApi: ExplorerApi
    @Mock
    lateinit var mockDatabase: ExplorerDatabase
    @Mock
    lateinit var mockNetworkHelper: NetworkHelper
    @Mock
    lateinit var mockSchedulers: RxSchedulers
    @Mock
    lateinit var mockCitiesDao: CitiesDao
    @Mock
    lateinit var mockMallsDao: MallsDao
    @Mock
    lateinit var mockShopsDao: ShopsDao

    private lateinit var repository: ExplorerRepository

    @Before
    fun setUp() {
        initMocks(this)

        repository = ExplorerRepositoryImpl(mockContext, mockApi, mockDatabase, mockNetworkHelper, mockSchedulers)

        `when`(mockSchedulers.network).thenReturn(Schedulers.io())
        `when`(mockSchedulers.database).thenReturn(Schedulers.single())

        `when`(mockDatabase.citiesDao()).thenReturn(mockCitiesDao)
        `when`(mockDatabase.mallsDao()).thenReturn(mockMallsDao)
        `when`(mockDatabase.shopsDao()).thenReturn(mockShopsDao)
    }

    @Test
    fun instance_created() {
        assertNotNull(repository)
    }

    @Test
    fun list_cities_source_network_api_call_when_network_connection_exists() {
        `when`(mockNetworkHelper.hasConnection(mockContext)).thenReturn(true)

        val mockResponse = mock(CitiesResponse::class.java)

        `when`(mockApi.getAllCities()).thenReturn(Observable.just(Response.success(mockResponse)))

        repository.listCities()

        verify(mockApi).getAllCities()
    }

    @Test
    fun list_cities_source_local_database_when_no_network_connection() {
        `when`(mockNetworkHelper.hasConnection(mockContext)).thenReturn(false)

        `when`(mockCitiesDao.listAll()).thenReturn(Flowable.just(emptyList()))

        repository.listCities()

        verify(mockCitiesDao).listAll()
    }

    @Test
    fun verify_cache_responses() {
        val shops_list = arrayListOf(Shop(1, 2), Shop(2, 3), Shop(3, 4))
        val malls_list = arrayListOf(Mall(2).apply { shops = shops_list }, Mall(3).apply { shops = shops_list })
        val cities = arrayListOf(City().apply { malls = malls_list })

        (repository as ExplorerRepositoryImpl).cacheResources(cities)

        verify(mockCitiesDao).insert(cities.first())
        verify(mockMallsDao, times(2)).insert(malls_list.first())
        verify(mockShopsDao, times(6)).insert(shops_list.first())
    }

    @Test
    fun verify_find_city() {
        val id = 1
        `when`(mockCitiesDao.findCity(id)).thenReturn(Maybe.just(City()))

        repository.findCity(id)
                .subscribe {
                    assertEquals(CITY, it.type)
                }
    }

    @Test
    fun verify_list_malls() {
        val id = 2

        val malls = arrayListOf(Mall(1), Mall(2))

        `when`(mockMallsDao.listAll(id)).thenReturn(Maybe.just(malls))
        repository.listMalls(id)
                .subscribe {
                    it.forEach { mall ->
                        assertEquals(MALL, mall.type)
                    }
                }
    }

    @Test
    fun verify_find_mall() {
        val id = 3
        val cityId = 4

        `when`(mockMallsDao.findMall(id, cityId)).thenReturn(Maybe.just(Mall(cityId)))

        repository.findMall(id, cityId)
                .subscribe {
                    assertEquals(MALL, it.type)
                }
    }

    @Test
    fun verify_list_shops() {
        val mallId = 3

        val shops = arrayListOf(Shop(1, 2), Shop(1, 2), Shop(2, 2))
        `when`(mockShopsDao.listAllInMall(mallId)).thenReturn(Maybe.just(shops))

        repository.listShops(mallId)
                .subscribe {
                    it.forEach { shop ->
                        assertEquals(SHOP, shop.type)
                    }
                }
    }

    @Test
    fun verify_list_shops_in_city() {
        val cityId = 3

        val shops = arrayListOf(Shop(1, 2), Shop(1, 2), Shop(2, 2))
        `when`(mockShopsDao.listAll(cityId)).thenReturn(Maybe.just(shops))

        repository.listShopsInCity(cityId)
                .subscribe {
                    it.forEach { shop ->
                        assertEquals(SHOP, shop.type)
                    }
                }
    }

    @Test
    fun verify_find_shop() {
        val id = 1
        val mallId = 4

        `when`(mockShopsDao.findInMall(id, mallId)).thenReturn(Maybe.just(Shop(5, mallId)))
        repository.findShop(id, mallId)
                .subscribe {
                    assertEquals(SHOP, it.type)
                }
    }

}