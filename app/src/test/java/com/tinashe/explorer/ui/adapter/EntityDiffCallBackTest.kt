package com.tinashe.explorer.ui.adapter

import com.tinashe.explorer.sdk.data.model.Entity
import com.tinashe.explorer.sdk.data.model.EntityType
import junit.framework.Assert.*
import org.junit.Test

class EntityDiffCallBackTest {

    @Test
    fun verify_areContentsTheSame_same_lists() {
        val callBack = EntityDiffCallBack(list, list)

        assertTrue(callBack.areContentsTheSame(0, 0))
    }

    @Test
    fun verify_areContentsTheSame_different_lists() {
        val callBack = EntityDiffCallBack(list.asReversed(), list)

        assertFalse(callBack.areContentsTheSame(0, 0))
    }

    @Test
    fun verify_areItemsTheSame_same_lists() {
        val callBack = EntityDiffCallBack(list, list)

        assertTrue(callBack.areItemsTheSame(0, 0))
    }

    @Test
    fun verify_areItemsTheSame_different_lists() {
        val callBack = EntityDiffCallBack(list.asReversed(), list)

        assertFalse(callBack.areItemsTheSame(0, 0))
    }

    @Test
    fun verify_getOldListSize() {
        var callBack = EntityDiffCallBack(list, list)

        assertEquals(list.size, callBack.oldListSize)

        callBack = EntityDiffCallBack(emptyList(), list)
        assertEquals(0, callBack.oldListSize)
    }

    @Test
    fun verify_getNewListSize() {
        var callBack = EntityDiffCallBack(list, list)

        assertEquals(list.size, callBack.newListSize)

        callBack = EntityDiffCallBack(list, emptyList())
        assertEquals(0, callBack.newListSize)
    }

    companion object {
        private val list = arrayListOf(Entity(1, "Cape Town", EntityType.CITY),
                Entity(2, "Pretoria", EntityType.CITY),
                Entity(5, "Steers", EntityType.SHOP))
    }
}