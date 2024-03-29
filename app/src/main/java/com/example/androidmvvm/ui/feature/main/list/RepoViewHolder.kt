package com.example.androidmvvm.ui.feature.main.list

import com.example.androidmvvm.databinding.ItemRepoBinding
import com.example.androidmvvm.domain.model.repo.RepoModel
import com.example.androidmvvm.ui.platform.BaseBindingViewHolder
import com.example.androidmvvm.ui.util.extension.setImageUrl

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