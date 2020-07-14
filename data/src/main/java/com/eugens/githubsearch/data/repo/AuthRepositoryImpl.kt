package com.eugens.githubsearch.data.repo

import android.accounts.NetworkErrorException
import android.util.Base64
import com.eugens.githubsearch.data.api.GitHubApi
import com.eugens.githubsearch.domain.model.LoginParams
import com.eugens.githubsearch.domain.model.TokenEntity
import com.eugens.githubsearch.domain.repository.AuthRepository
import com.eugens.githubsearch.domain.repository.TokenRepository
import io.reactivex.rxjava3.core.Observable


class AuthRepositoryImpl(val api: GitHubApi, private val tokenStorage: TokenRepository) :
    AuthRepository {

    override fun login(loginParams: LoginParams?): Observable<Boolean> {

        val base = Base64.encodeToString(
            (loginParams?.login + ":" + loginParams?.pass).toByteArray(),
            Base64.NO_WRAP
        )
        tokenStorage.saveTokenEntity(TokenEntity(base))
        tokenStorage.setIsAuthorized(false)

        return api.userRepos()
            .flatMap { response ->
                when (response.code()) {
                    200 -> {
                        tokenStorage.setIsAuthorized(true)
                        Observable.just(true)
                    }
                    401 -> Observable.error(NetworkErrorException("Bad credentials"))
                    else -> {
                        Observable.just(false)
                    }
                }
            }
    }

}