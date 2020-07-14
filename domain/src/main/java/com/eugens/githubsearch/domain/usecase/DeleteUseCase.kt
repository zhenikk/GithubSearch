package com.eugens.githubsearch.domain.usecase

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.model.Repository
import com.eugens.githubsearch.domain.repository.GithubSearchRepository
import com.eugens.githubsearch.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable

class DeleteUseCase(
    val repository: GithubSearchRepository,
    schedulersProvider: SchedulersProvider
) :
    UseCase<Boolean, Repository>(
        schedulersProvider
    ) {
    override fun createObservableUseCase(params: Repository?): Observable<Boolean> {
        return repository.deleteItem(params)
    }
}