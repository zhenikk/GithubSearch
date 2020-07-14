package com.eugens.githubsearch.presentation.feature.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eugens.githubsearch.domain.model.Repository
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        repository: Repository?,
        clickCallBack: RepositoriesAdapter.ItemClickCallBack?
    ) {
        repository?.let { repo ->
            itemView.tvRepoName.text = repo.name
            itemView.tvRepoDescription.text = repo.description
            itemView.tvStars.text = repo.starsCount.toString()
            itemView.setOnClickListener {
                clickCallBack?.onItemClick(repo)
            }
            itemView.tvViewed.visibility = if (repo.isViewed) View.VISIBLE else View.GONE
        }
    }
}
