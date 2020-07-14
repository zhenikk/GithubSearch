package com.eugens.githubsearch.presentation.feature.search

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugens.githubsearch.databinding.FragmentSearchBinding
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.presentation.base.BaseFragment
import com.eugens.githubsearch.presentation.utils.extensions.hideKeyboard
import com.eugens.githubsearch.presentation.utils.extensions.openUrlInBrowser
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel()
    private val searchDelayHandler = Handler()
    private var reposAdapter: RepositoriesAdapter =  RepositoriesAdapter()

    companion object {
        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.setOnQueryChangeListener { _, newQuery ->
            searchDelayHandler.removeCallbacksAndMessages(null)
            searchDelayHandler.postDelayed({
                viewModel.search(newQuery)
            }, 500)
        }
        searchView.setOnClearSearchActionListener {
            searchDelayHandler.removeCallbacksAndMessages(null)
            viewModel.cancelSearch()
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it.peekContent()) {
                searchView.showProgress()
            } else searchView.hideProgress()
        })
        rvSearchResult.setOnTouchListener { v, event ->
            hideKeyboard()
            v.performClick()
            false
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvSearchResult.layoutManager = layoutManager
        reposAdapter.setClickCallBack(
            object : RepositoriesAdapter.ItemClickCallBack {
                override fun onItemClick(repository: Repository) {
                    openUrlInBrowser(repository.reference)
                }
            }
        )
        rvSearchResult.adapter = reposAdapter
        viewModel.pagedList.observe(
            viewLifecycleOwner, Observer {
                reposAdapter.submitList(it)
            }
        )
    }



    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}