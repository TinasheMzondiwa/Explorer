package com.tinashe.explorer.sdk.data.model

/**
 * Representation of a shop
 */
@android.arch.persistence.room.Entity(tableName = "shops")
internal data class Shop(var city: Int, var mall: Int) : Entity(type = EntityType.SHOP)