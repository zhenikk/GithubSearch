package com.eugens.githubsearch.data.repo

import com.eugens.githubsearch.data.api.GitHubApi
import com.eugens.githubsearch.data.api.model.GithubRepo
import com.eugens.githubsearch.data.local.GithubRepoDao
import com.eugens.githubsearch.data.mapper.toDbList
import com.eugens.githubsearch.data.mapper.toRepoList
import com.eugens.githubsearch.data.mapper.toRepositoryList
import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.model.HistoryParams
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.model.SearchParams
import com.eugens.githubsearch.domain.repository.GithubSearchRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction


class GithubSearchRepositoryImpl(
    private val gitHubApi: GitHubApi,
    private val githubDb: GithubRepoDao,
    private val schedulersProvider: SchedulersProvider
) :
    GithubSearchRepository {

    companion object {
        private const val PER_PAGE = 15
    }

    override fun search(
        searchParams: SearchParams?
    ): Observable<List<Repository>> {
        val pageToLoad = (searchParams?.page ?: 0) / PER_PAGE + 1
        val pageToLoadNext = pageToLoad + 1

        val firstQuery =
            getSearchQueryForPage(pageToLoad, searchParams)
        val secondQuery = getSearchQueryForPage(pageToLoadNext, searchParams)

        return firstQuery.zipWith(
            secondQuery,
            BiFunction { t1: List<GithubRepo>?, t2: List<GithubRepo>? ->
                val joined = ArrayList<GithubRepo>()
                joined.addAll(t1 ?: arrayListOf())
                joined.addAll(t2 ?: arrayListOf())
                return@BiFunction joined
            })
            .map { it.toRepositoryList() ?: arrayListOf() }
            .doOnNext {
                val maxOrder = githubDb.getLargestOrder()
                it?.let { list ->
                    githubDb.insertAll(list.toDbList(maxOrder))
                }
            }
    }

    override fun getHistory(historyParams: HistoryParams?): Observable<List<Repository>> {
        return Observable.just(
            githubDb.reposAfter(
                historyParams?.offset,
                historyParams?.requestedLoadSize
            ).toRepoList()
        )
    }

    override fun markAsViewed(params: Repository?): Observable<Boolean> {
        return Observable.create {
            params?.let { repo ->
                val entity = githubDb.getRepoById(repo.remoteId)
                entity.isViewed = true
                githubDb.update(entity)
                it.onNext(true)
                it.onComplete()
            }?.run {
                it.onNext(false)
                it.onComplete()
            }
        }

    }

    override fun swapItems(first: Repository?, second: Repository?): Observable<Boolean> {
        return Observable.create {
            first?.let { f ->
                second?.let { s ->
                    val firstEntity = githubDb.getRepoById(f.remoteId)
                    val secondEntity = githubDb.getRepoById(s.remoteId)
                    val orderFirst = firstEntity.order
                    val orderSecond = secondEntity.order
                    firstEntity.order = orderSecond
                    secondEntity.order = orderFirst
                    githubDb.update(firstEntity)
                    githubDb.update(secondEntity)
                    it.onNext(true)
                    it.onComplete()
                }
            }.run {
                it.onNext(false)
                it.onComplete()
            }
        }
    }

    override fun deleteItem(item: Repository?): Observable<Boolean> {
        return Observable.create {
            item?.let { repo ->
                val entity = githubDb.getRepoById(repo.remoteId)
                githubDb.delete(entity)
                it.onNext(true)
                it.onComplete()
            }?.run {
                it.onNext(false)
                it.onComplete()
            }
        }
    }

    private fun getSearchQueryForPage(
        pageToLoad: Int,
        searchParams: SearchParams?
    ): Observable<List<GithubRepo>> {
        return gitHubApi.searchRepos(searchParams?.query, pageToLoad, PER_PAGE)
            .subscribeOn(schedulersProvider.backgroundThread())
            .map { it.body() }
            .map { it?.items }
    }
}


//TODO:fun changeOrder() {}
