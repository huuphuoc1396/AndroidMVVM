package com.example.androidmvvm.feature.main.list

import androidx.recyclerview.widget.DiffUtil
import com.example.androidmvvm.feature.main.model.RepoItem

class RepoDiffCallback : DiffUtil.ItemCallback<RepoItem>() {
    override fun areItemsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
        return oldItem == newItem
    }
}