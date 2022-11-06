package com.example.androidmvvm.platform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseViewHolder<Item>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return onCreateViewHolder(viewType, layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<Item>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(getItem(position), payloads)
    }

    abstract fun onCreateViewHolder(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<Item>
}