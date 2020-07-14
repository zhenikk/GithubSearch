package com.eugens.githubsearch.domain.repository

import com.eugens.githubsearch.domain.model.TokenEntity

interface TokenRepository {
    fun saveTokenEntity(authEntity: TokenEntity)
    fun getTokenEntity(): TokenEntity?
    fun setIsAuthorized(isAuthorized: Boolean)
    fun getIsAuthorized(): Boolean?
}