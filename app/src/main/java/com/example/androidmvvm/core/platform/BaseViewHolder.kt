package com.example.androidmvvm.core.platform

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvm.core.extension.setOnSingleClickListener

abstract class BaseViewHolder<Item>(
    itemView: View,
    onItemClickListener: ((Item) -> Unit)? = null,
) : RecyclerView.ViewHolder(itemView) {
    val context: Context
        get() = itemView.context

    var item: Item? = null

    init {
        onItemClickListener?.let {
            this.itemView.setOnSingleClickListener {
                item?.let(onItemClickListener)
            }
        }

    }

    protected abstract fun onBind(item: Item)

    open fun onBind(item: Item, payloads: List<Any>) {
        onBind(item)
    }

    fun bind(item: Item) {
        this.item = item
        onBind(item)
    }


    fun bind(item: Item, payloads: List<Any>) {
        this.item = item
        onBind(item, payloads)
    }
}