package com.tinashe.explorer.sdk.data.repository

import com.tinashe.explorer.sdk.data.model.Entity
import io.reactivex.Maybe
import io.reactivex.Observable

interface ExplorerRepository {

    /**
     * List all cities
     *
     * @return: Collection of cities
     */
    fun listCities(): Observable<List<Entity>>


    /**
     * Find a particular city
     *
     * @param id: Id of the city
     *
     * @return: City if it exists
     */
    fun findCity(id: Int): Maybe<Entity>


    /**
     * List all malls in a particular city
     *
     * @param cityId: Id of the city
     *
     * @return: Collection of malls
     */
    fun listMalls(cityId: Int): Maybe<List<Entity>>


    /**
     * Find a particular mall in a city
     *
     * @param id: Id of the mall
     * @param cityId: Id of the city
     *
     * @return: Mall if it exists
     */
    fun findMall(id: Int, cityId: Int): Maybe<Entity>


    /**
     * List all shops in a particular mall
     *
     * @param mallId: Id of the mall
     *
     * @return: Collection of shops
     */
    fun listShops(mallId: Int): Maybe<List<Entity>>


    /**
     * List all shops in a particular city
     *
     * @param cityId: Id of he city
     *
     * @return: Collection of shops
     */
    fun listShopsInCity(cityId: Int): Maybe<List<Entity>>


    /**
     * Find a particular shop in a mall
     *
     * @param mallId: Id of the mall
     * @param id: Id of the shop
     *
     * @return: Shop if it exists
     */
    fun findShop(id: Int, mallId: Int): Maybe<Entity>

}