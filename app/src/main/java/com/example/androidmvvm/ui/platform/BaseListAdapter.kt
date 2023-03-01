package com.example.androidmvvm.ui.platform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
abstract class BaseListAdapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return onCreateViewHolder(viewType, layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bindingViewHolder = holder as? BaseViewHolder<Item>
        bindingViewHolder?.bind(getItem(position), position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val bindingViewHolder = holder as? BaseViewHolder<Item>
        bindingViewHolder?.bind(getItem(position), position, payloads)
    }

    abstract fun onCreateViewHolder(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder
}