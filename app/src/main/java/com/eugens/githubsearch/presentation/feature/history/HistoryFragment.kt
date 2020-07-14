package com.eugens.githubsearch.presentation.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugens.githubsearch.databinding.FragmentHistoryBinding
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.presentation.base.BaseFragment
import com.eugens.githubsearch.presentation.feature.search.RepositoriesAdapter
import com.eugens.githubsearch.presentation.utils.extensions.openUrlInBrowser
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModel()
    private var reposAdapter: RepositoriesAdapter = RepositoriesAdapter()

    companion object {
        fun getInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvHistory.layoutManager = layoutManager


        reposAdapter.setClickCallBack(
            object : RepositoriesAdapter.ItemClickCallBack {
                override fun onItemClick(repository: Repository) {
                    openUrlInBrowser(repository.reference)
                    viewModel.markAsViewed(repository)
                }
            }
        )
        rvHistory.adapter = reposAdapter
        viewModel.pagedList.observe(
            viewLifecycleOwner, Observer {
                reposAdapter.submitList(it)
            }
        )
        ItemTouchHelper(RepositoriesAdapter.SwipeCallback(reposAdapter, {
            viewModel.deleteItem(it)
        }) { from, to ->
            viewModel.swapItems(from, to)
        }).apply {
            attachToRecyclerView(rvHistory)
        }
        rvHistory.itemAnimator?.changeDuration = 0

    }


}