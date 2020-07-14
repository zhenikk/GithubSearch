package com.eugens.githubsearch.presentation.utils.ui

import androidx.recyclerview.widget.DiffUtil
import com.eugens.githubsearch.domain.model.Repository


class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.remoteId == newItem.remoteId
    }

}