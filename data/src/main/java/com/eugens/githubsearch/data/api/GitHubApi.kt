package com.eugens.githubsearch.data.api

import com.eugens.githubsearch.data.api.model.GithubRepoResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("user/repos")
    fun userRepos(): Observable<Response<Any>>

    @GET("search/repositories?sort=stars&order=desc")
    fun searchRepos(
        @Query("q") query: String?,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Observable<Response<GithubRepoResponse>>
}