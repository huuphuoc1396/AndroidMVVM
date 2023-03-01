package com.example.androidmvvm.ui.platform

import androidx.viewbinding.ViewBinding

abstract class BaseBindingViewHolder<Item, VB : ViewBinding>(
    open val viewBinding: VB,
    onItemClickListener: ((Item) -> Unit)? = null,
) : BaseViewHolder<Item>(viewBinding.root, onItemClickListener) {

    abstract fun VB.onBind(item: Item, position: Int)

    override fun onBind(item: Item, position: Int) {
        viewBinding.onBind(item, position)
    }
}