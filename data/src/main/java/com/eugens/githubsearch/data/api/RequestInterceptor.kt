package com.eugens.githubsearch.data.api

import com.eugens.githubsearch.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/** Class for adding header with token to every request **/
class RequestInterceptor(private val tokenStorage: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest: Request
        val initialRequest = chain.request()

        val authData = tokenStorage.getTokenEntity()

        modifiedRequest =
            initialRequest.newBuilder()
                .addHeader("Authorization", "Basic " + authData?.access_token)
                .addHeader("Content-Type", "application/json")
                .build()
        return chain.proceed(modifiedRequest)
    }
}