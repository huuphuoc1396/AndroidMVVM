package com.example.androidmvvm.feature.main.list

import com.example.androidmvvm.util.extension.setImageUrl
import com.example.androidmvvm.platform.BaseBindingViewHolder
import com.example.androidmvvm.databinding.ItemRepoBinding
import com.example.androidmvvm.model.main.RepoModel

class RepoViewHolder(
    viewBinding: ItemRepoBinding,
    onItemClickAction: (repo: RepoModel) -> Unit,
) : BaseBindingViewHolder<RepoModel, ItemRepoBinding>(viewBinding, onItemClickAction) {

    override fun onBind(item: RepoModel) {
        with(viewBinding) {
            imgAvatar.setImageUrl(item.imageUrl)
            txtRepoName.text = item.repoName
            txtOwnerName.text = item.ownerName
        }
    }
}