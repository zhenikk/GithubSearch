package com.eugens.githubsearch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubRepoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val remoteId: String,
    val name: String?,
    val description: String?,
    val reference: String?,
    val starsCount: Int?,
    var order:Int,
    var isViewed: Boolean = false
)