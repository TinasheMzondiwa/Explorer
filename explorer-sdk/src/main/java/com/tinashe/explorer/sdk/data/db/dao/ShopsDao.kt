package com.tinashe.explorer.sdk.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.tinashe.explorer.sdk.data.model.Shop
import io.reactivex.Maybe

@Dao
internal interface ShopsDao : BaseDao<Shop> {

    @Query("SELECT * FROM shops WHERE city = :cityId")
    fun listAll(cityId: Int): Maybe<List<Shop>>

    @Query("SELECT * FROM shops WHERE mall = :mallId")
    fun listAllInMall(mallId: Int): Maybe<List<Shop>>

    @Query("SELECT * FROM shops WHERE id = :id AND mall = :mallId LIMIT 1")
    fun findInMall(id: Int, mallId: Int): Maybe<Shop>
}