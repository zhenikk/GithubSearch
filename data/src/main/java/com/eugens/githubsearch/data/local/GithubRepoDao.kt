package com.eugens.githubsearch.data.local

import androidx.room.*
import com.eugens.githubsearch.data.local.model.GithubRepoEntity


@Dao
interface GithubRepoDao {
    @Query("SELECT * FROM githubrepoentity")
    fun getAll(): List<GithubRepoEntity>

    @Insert
    fun insertAll(list: List<GithubRepoEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(githubRepos: List<GithubRepoEntity?>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(githubRepo: GithubRepoEntity)

    @Query("SELECT MAX(`order`) FROM githubrepoentity")
    fun getLargestOrder(): Int

    @Delete
    fun delete(model: GithubRepoEntity)

    @Query("SELECT * FROM githubrepoentity ORDER BY `order` ASC limit :requestedLoadSize")
    fun repos(requestedLoadSize: Int): List<GithubRepoEntity>

    @Query("SELECT * FROM githubrepoentity ORDER BY `order` ASC limit :requestedLoadSize  offset :key")
    fun reposAfter(key: Int?, requestedLoadSize: Int?): List<GithubRepoEntity>

    @Query("SELECT * FROM githubrepoentity WHERE remoteId =:remoteId limit 1 ")
    fun getRepoById(remoteId: String): GithubRepoEntity


}