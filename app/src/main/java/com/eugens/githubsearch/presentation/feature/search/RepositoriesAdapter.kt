package com.eugens.githubsearch.presentation.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eugens.githubsearch.R
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.presentation.utils.ui.RepositoryDiffCallback

class RepositoriesAdapter :
    PagedListAdapter<Repository, RepositoryViewHolder>(RepositoryDiffCallback()) {

    fun setClickCallBack(clickCallBack: ItemClickCallBack?) {
        this.clickCallBack = clickCallBack
    }

    interface ItemClickCallBack {
        fun onItemClick(repository: Repository)
    }

    private var clickCallBack: ItemClickCallBack? = null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_repository, viewGroup, false)
        return RepositoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position), clickCallBack)
    }


    class SwipeCallback(
        private val adapter: RepositoriesAdapter,
        private val onItemDelete: (item: Repository) -> Unit,
        private val onItemMove: (from: Repository, to: Repository) -> Unit
    ) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {

        private var from: Int = 0
        private var to: Int = 0
        private var remove: Int = 0

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            from = viewHolder.adapterPosition
            to = target.adapterPosition
            val item1 = adapter.getItem(from)
            val item2 = adapter.getItem(to)
            if (item1 != null && item2 != null) {
                adapter.notifyItemMoved(from, to)
                return true
            }
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            remove = viewHolder.adapterPosition
            adapter.getItem(remove)?.let {
                adapter.notifyItemRemoved(remove)
                onItemDelete(it)

            }

        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder?.itemView?.alpha = 0.5f
            }
        }

        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.alpha = 1.0f

            val item1 = adapter.getItem(from)
            val item2 = adapter.getItem(to)
            if (item1 != null && item2 != null) {
                onItemMove(item1, item2)
            }
        }
    }

}
