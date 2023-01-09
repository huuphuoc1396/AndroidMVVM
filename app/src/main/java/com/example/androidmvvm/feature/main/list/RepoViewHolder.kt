package com.example.androidmvvm.feature.main.list

import com.example.androidmvvm.databinding.ItemRepoBinding
import com.example.androidmvvm.model.main.RepoModel
import com.example.androidmvvm.platform.BaseBindingViewHolder
import com.example.androidmvvm.util.extension.setImageUrl

class RepoViewHolder(
    viewBinding: ItemRepoBinding,
    onItemClickAction: (repo: RepoModel) -> Unit,
) : BaseBindingViewHolder<RepoModel, ItemRepoBinding>(viewBinding, onItemClickAction) {

    override fun ItemRepoBinding.onBind(item: RepoModel, position: Int) {
        imgAvatar.setImageUrl(item.imageUrl)
        txtRepoName.text = item.repoName
        txtOwnerName.text = item.ownerName
    }
}