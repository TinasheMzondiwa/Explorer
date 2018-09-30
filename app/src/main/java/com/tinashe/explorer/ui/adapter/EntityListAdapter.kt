package com.tinashe.explorer.ui.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.tinashe.explorer.R
import com.tinashe.explorer.sdk.data.model.Entity
import com.tinashe.explorer.sdk.data.model.EntityType.*
import com.tinashe.explorer.utils.inflateView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.entity_item.*

class EntityListAdapter constructor(private val callback: (Entity) -> Unit) : RecyclerView.Adapter<EntityListAdapter.Holder>() {

    var items = arrayListOf<Entity>()
        set(value) {
            val callback = EntityDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            field.clear()
            field.addAll(value)

            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder = Holder.inflate(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val entity = items[position]

        holder.bind(entity)

        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class Holder constructor(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entity: Entity) {
            name.text = entity.name
            icon.setImageResource(when (entity.type) {
                CITY -> R.drawable.ic_city
                MALL -> R.drawable.ic_mall
                SHOP -> R.drawable.ic_store
            })
        }

        companion object {
            fun inflate(parent: ViewGroup):
                    Holder = Holder(inflateView(R.layout.entity_item, parent, false))
        }

    }
}