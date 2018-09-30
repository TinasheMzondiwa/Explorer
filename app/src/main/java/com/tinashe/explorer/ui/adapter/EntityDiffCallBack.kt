package com.tinashe.explorer.ui.adapter

import android.support.v7.util.DiffUtil
import com.tinashe.explorer.sdk.data.model.Entity

class EntityDiffCallBack(private val oldList: List<Entity>,
                         private val newList: List<Entity>) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem = oldList[oldPosition]
        val newItem = newList[newPosition]

        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem = oldList[oldPosition]
        val newItem = newList[newPosition]

        return oldItem.id == newItem.id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}