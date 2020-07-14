package com.eugens.githubsearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eugens.githubsearch.data.local.model.GithubRepoEntity

@Database(
    entities = [GithubRepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GitHubDb : RoomDatabase() {
    abstract fun githubReposDao(): GithubRepoDao

}