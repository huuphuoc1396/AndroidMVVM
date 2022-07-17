package com.example.androidmvvm.feature.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidmvvm.core.platform.BaseListAdapter
import com.example.androidmvvm.core.platform.BaseViewHolder
import com.example.androidmvvm.databinding.ItemRepoBinding
import com.example.androidmvvm.feature.main.model.RepoItem

class RepoAdapter(
    private val onItemClickAction: (repo: RepoItem) -> Unit,
) : BaseListAdapter<RepoItem>(RepoDiffCallback()) {
    override fun onCreateViewHolder(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<RepoItem> {
        val viewBinding = ItemRepoBinding.inflate(layoutInflater, parent, false)
        return RepoViewHolder(
            viewBinding = viewBinding,
            onItemClickAction = onItemClickAction,
        )
    }
}