package com.tinashe.explorer.sdk.data.model

import junit.framework.Assert.assertEquals
import org.junit.Test

class DataClassesTest {

    @Test
    fun city_object_is_of_entity_type_CITY() {
        val city = City()

        assertEquals(EntityType.CITY, city.type)
    }

    @Test
    fun mall_object_is_of_entity_type_MALL() {
        val mall = Mall()

        assertEquals(EntityType.MALL, mall.type)
    }

    @Test
    fun shop_object_is_of_entity_type_SHOP() {
        val shop = Shop(1, 2)

        assertEquals(EntityType.SHOP, shop.type)
    }
}