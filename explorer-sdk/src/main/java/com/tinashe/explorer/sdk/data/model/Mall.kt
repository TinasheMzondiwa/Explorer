package com.tinashe.explorer.sdk.data.model

import android.arch.persistence.room.Ignore

/**
 * Representation of a mall
 */
@android.arch.persistence.room.Entity(tableName = "malls")
internal data class Mall(

        /**
         * Id of the city where this mall belongs
         */
        var city: Int = 0) : Entity(type = EntityType.MALL) {
    /**
     * Collection shops in this mall
     */
    @Ignore
    var shops: List<Shop> = emptyList()
}