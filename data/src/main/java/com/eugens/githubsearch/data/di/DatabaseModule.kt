package com.eugens.githubsearch.data.di

import androidx.room.Room
import com.eugens.githubsearch.data.local.GitHubDb
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), GitHubDb::class.java, "github_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<GitHubDb>().githubReposDao() }

}