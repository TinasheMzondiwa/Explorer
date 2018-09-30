package com.tinashe.explorer.sdk.data.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tinashe.explorer.sdk.data.model.City
import com.tinashe.explorer.sdk.data.model.Mall
import com.tinashe.explorer.sdk.data.model.Shop

internal class DataTypeConverters {

    private val gson: Gson = Gson()

    @TypeConverter
    fun citiesToJson(cities: List<City>): String {
        return gson.toJson(cities)
    }

    @TypeConverter
    fun jsonToCities(jsonString: String): List<City> {

        val type = object : TypeToken<List<City>>() {

        }.type
        return gson.fromJson(jsonString, type)
    }

    @TypeConverter
    fun mallsToJson(malls: List<Mall>): String {
        return gson.toJson(malls)
    }

    @TypeConverter
    fun jsonToMalls(jsonString: String): List<Mall> {

        val type = object : TypeToken<List<Mall>>() {

        }.type
        return gson.fromJson(jsonString, type)
    }

    @TypeConverter
    fun shopsToJson(shops: List<Shop>): String {
        return gson.toJson(shops)
    }

    @TypeConverter
    fun jsonToShops(jsonString: String): List<Shop> {

        val type = object : TypeToken<List<Shop>>() {

        }.type
        return gson.fromJson(jsonString, type)
    }
}