package com.example.androidmvvm.feature.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidmvvm.platform.BaseListAdapter
import com.example.androidmvvm.platform.BaseViewHolder
import com.example.androidmvvm.databinding.ItemRepoBinding
import com.example.androidmvvm.model.main.RepoModel

class RepoAdapter(
    private val onItemClickAction: (repo: RepoModel) -> Unit,
) : BaseListAdapter<RepoModel>(RepoDiffCallback()) {
    override fun onCreateViewHolder(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<RepoModel> {
        val viewBinding = ItemRepoBinding.inflate(layoutInflater, parent, false)
        return RepoViewHolder(
            viewBinding = viewBinding,
            onItemClickAction = onItemClickAction,
        )
    }
}