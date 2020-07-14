package com.eugens.githubsearch.domain.repository

import com.eugens.githubsearch.domain.model.LoginParams
import io.reactivex.rxjava3.core.Observable

interface AuthRepository {
    fun login(loginParams:LoginParams?): Observable<Boolean>
}
