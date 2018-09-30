package com.tinashe.explorer.sdk.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.tinashe.explorer.sdk.data.model.City
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
internal interface CitiesDao : BaseDao<City> {

    @Query("SELECT * FROM cities")
    fun listAll(): Flowable<List<City>>

    @Query("SELECT * FROM cities WHERE id = :id LIMIT 1")
    fun findCity(id: Int): Maybe<City>
}