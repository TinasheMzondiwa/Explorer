package com.tinashe.explorer.sdk.data.model

import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Representation of an entity
 */
open class Entity(

        /**
         * Entity id
         */
        @PrimaryKey
        var id: Int = 0,

        /**
         * Entity name
         */
        var name: String = "",

        /**
         * Entity type
         */
        @Ignore
        var type: EntityType) {

    override fun equals(other: Any?): Boolean {
        return other is Entity && id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}