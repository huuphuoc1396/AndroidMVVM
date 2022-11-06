package com.example.androidmvvm.feature.main.list

import androidx.recyclerview.widget.DiffUtil
import com.example.androidmvvm.model.main.RepoModel

class RepoDiffCallback : DiffUtil.ItemCallback<RepoModel>() {
    override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
        return oldItem == newItem
    }
}