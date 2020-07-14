package com.eugens.githubsearch.data.di

import com.eugens.githubsearch.data.repo.AuthRepositoryImpl
import com.eugens.githubsearch.data.repo.GithubSearchRepositoryImpl
import com.eugens.githubsearch.data.repo.TokenRepositoryImpl
import com.eugens.githubsearch.domain.repository.AuthRepository
import com.eugens.githubsearch.domain.repository.GithubSearchRepository
import com.eugens.githubsearch.domain.repository.TokenRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepositoryImpl(get(), get()) as AuthRepository }
    single { TokenRepositoryImpl(get(), get()) as TokenRepository }
    single { GithubSearchRepositoryImpl(get(), get(),get()) as GithubSearchRepository }
}