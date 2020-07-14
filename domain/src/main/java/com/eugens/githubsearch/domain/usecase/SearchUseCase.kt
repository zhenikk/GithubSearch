package com.eugens.githubsearch.domain.usecase

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.model.SearchParams
import com.eugens.githubsearch.domain.repository.GithubSearchRepository
import com.eugens.githubsearch.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable

class SearchUseCase(private val githubSearchRepository: GithubSearchRepository, schedulersProvider: SchedulersProvider) :
    UseCase<List<Repository>, SearchParams>(schedulersProvider) {
    override fun createObservableUseCase(params: SearchParams?): Observable<List<Repository>> {
   return githubSearchRepository.search(params)
    }
}