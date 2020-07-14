package com.eugens.githubsearch.presentation.di

import com.eugens.githubsearch.data.di.createNetworkModule
import com.eugens.githubsearch.data.di.databaseModule
import com.eugens.githubsearch.data.di.repositoryModule
import com.eugens.githubsearch.presentation.di.modules.navigationModule
import com.eugens.githubsearch.presentation.di.modules.threadExecutorModule
import com.eugens.githubsearch.presentation.di.modules.useCaseModule
import com.eugens.githubsearch.presentation.di.modules.viewModelModule


val appComponent = listOf(
    repositoryModule,
    databaseModule,
    createNetworkModule("https://api.github.com/"),
    useCaseModule,
    threadExecutorModule,
    navigationModule,
    viewModelModule
)