package com.eugens.githubsearch.domain.usecase

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import com.eugens.githubsearch.domain.model.LoginParams
import com.eugens.githubsearch.domain.repository.AuthRepository
import com.eugens.githubsearch.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable

class AuthUseCase(private val authRepository: AuthRepository, schedulersProvider: SchedulersProvider) :
    UseCase<Boolean, LoginParams>(schedulersProvider) {
    override fun createObservableUseCase(params: LoginParams?): Observable<Boolean> {
       return authRepository.login(params)
    }


}