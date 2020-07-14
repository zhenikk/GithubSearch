package com.eugens.githubsearch.presentation.di.modules

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.presentation.utils.AppSchedulersProvider
import org.koin.dsl.module

val threadExecutorModule = module {
    factory { AppSchedulersProvider() as SchedulersProvider }
}