package com.eugens.githubsearch.presentation.di.modules

import com.eugens.githubsearch.presentation.feature.auth.AuthViewModel
import com.eugens.githubsearch.presentation.feature.history.HistoryViewModel
import com.eugens.githubsearch.presentation.feature.home.HomeViewModel
import com.eugens.githubsearch.presentation.feature.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SearchViewModel() }
    viewModel { HistoryViewModel(get(), get(), get()) }
}