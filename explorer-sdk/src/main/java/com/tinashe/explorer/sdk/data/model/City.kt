package com.tinashe.explorer.sdk.data.model

import android.arch.persistence.room.Ignore

/**
 * Representation of an city
 */
@android.arch.persistence.room.Entity(tableName = "cities")
internal class City : Entity(type = EntityType.CITY) {
    /**
     * Collection of malls in this city
     */
    @Ignore
    var malls: List<Mall> = emptyList()
}