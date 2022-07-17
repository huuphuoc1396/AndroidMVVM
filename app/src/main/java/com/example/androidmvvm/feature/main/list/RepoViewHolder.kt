package com.example.androidmvvm.feature.main.list

import com.example.androidmvvm.core.extension.setImageUrl
import com.example.androidmvvm.core.platform.BaseBindingViewHolder
import com.example.androidmvvm.databinding.ItemRepoBinding
import com.example.androidmvvm.feature.main.model.RepoItem

class RepoViewHolder(
    viewBinding: ItemRepoBinding,
    onItemClickAction: (repo: RepoItem) -> Unit,
) : BaseBindingViewHolder<RepoItem, ItemRepoBinding>(viewBinding, onItemClickAction) {

    override fun onBind(item: RepoItem) {
        with(viewBinding) {
            imgAvatar.setImageUrl(item.imageUrl)
            txtRepoName.text = item.repoName
            txtOwnerName.text = item.ownerName
        }
    }
}