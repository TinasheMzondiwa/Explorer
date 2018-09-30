package com.tinashe.explorer.sdk.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.tinashe.explorer.sdk.data.db.dao.CitiesDao
import com.tinashe.explorer.sdk.data.db.dao.MallsDao
import com.tinashe.explorer.sdk.data.db.dao.ShopsDao
import com.tinashe.explorer.sdk.data.model.City
import com.tinashe.explorer.sdk.data.model.Mall
import com.tinashe.explorer.sdk.data.model.Shop

@Database(entities = [(City::class), (Mall::class), (Shop::class)], version = 1)
internal abstract class ExplorerDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao

    abstract fun mallsDao(): MallsDao

    abstract fun shopsDao(): ShopsDao

    companion object {
        private const val DATABASE_NAME = "explorer_sdk_db"

        fun create(context: Context): ExplorerDatabase = Room.databaseBuilder(context, ExplorerDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}