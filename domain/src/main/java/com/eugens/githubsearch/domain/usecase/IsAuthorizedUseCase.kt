package com.eugens.githubsearch.domain.usecase

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.repository.TokenRepository
import com.eugens.githubsearch.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable


class IsAuthorizedUseCase(
    private val tokenRepository: TokenRepository,
    schedulersProvider: SchedulersProvider
) : UseCase<Boolean, Any>(schedulersProvider) {
    override fun createObservableUseCase(params: Any?): Observable<Boolean> {
        return Observable.just(tokenRepository.getIsAuthorized())
    }
}