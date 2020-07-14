package com.eugens.githubsearch.domain.model

data class Repository(
    val remoteId: String,
    val name: String?,
    val description: String?,
    val reference: String?,
    val starsCount: Int?,
    val order: Int?,
    val isViewed: Boolean = false
)