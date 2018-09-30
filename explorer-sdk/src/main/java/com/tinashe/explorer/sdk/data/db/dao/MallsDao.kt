package com.tinashe.explorer.sdk.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.tinashe.explorer.sdk.data.model.Mall
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
internal interface MallsDao : BaseDao<Mall> {

    @Query("SELECT * FROM malls")
    fun listAll(): Flowable<List<Mall>>

    @Query("SELECT * FROM malls WHERE city = :cityId")
    fun listAll(cityId: Int): Maybe<List<Mall>>

    @Query("SELECT * FROM malls WHERE id = :mallId AND city = :cityId LIMIT 1")
    fun findMall(mallId: Int, cityId: Int): Maybe<Mall>
}