package com.eugens.githubsearch.data.repo

import android.content.Context
import android.content.SharedPreferences
import com.eugens.githubsearch.domain.model.TokenEntity
import com.eugens.githubsearch.domain.repository.TokenRepository
import com.google.gson.Gson

class TokenRepositoryImpl(context: Context, private val gson: Gson) : TokenRepository {
    companion object {
        private const val TOKEN_DATA_STORAGE_KEY = "TOKEN_DATA_STORAGE"
        private const val TOKEN_DATA_KEY = "TOKEN_DATA"
        private const val IS_AUTHORIZED_KEY = "IS_AUTHORIZED"
    }

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(TOKEN_DATA_STORAGE_KEY, Context.MODE_PRIVATE)
    }


    override fun getTokenEntity(): TokenEntity? {
        val s = preferences.getString(TOKEN_DATA_KEY, "")
        return if (s != "")
            gson.fromJson(s, TokenEntity::class.java)
        else null
    }

    override fun saveTokenEntity(authEntity: TokenEntity) {
        val s = gson.toJson(authEntity)
        preferences.edit().putString(TOKEN_DATA_KEY, s).apply()
    }


    override fun setIsAuthorized(isAuthorized: Boolean) {
        preferences.edit().putBoolean(IS_AUTHORIZED_KEY, isAuthorized).apply()
    }

    override fun getIsAuthorized(): Boolean? {
        return preferences.getBoolean(IS_AUTHORIZED_KEY, false)
    }

}