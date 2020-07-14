package com.eugens.githubsearch.data.mapper

import com.eugens.githubsearch.data.api.model.GithubRepo
import com.eugens.githubsearch.data.local.model.GithubRepoEntity
import com.eugens.githubsearch.domain.model.Repository


fun List<GithubRepo>?.toRepositoryList(): List<Repository>? {
    val result = arrayListOf<Repository>()
    this?.forEach {
        result.add(it.toRepository())
    }
    return result
}

fun List<GithubRepoEntity>?.toRepoList(): List<Repository>? {
    val result = arrayListOf<Repository>()
    this?.forEach {
        result.add(it.toRepository())
    }
    return result
}

fun List<Repository>?.toDbList(maxOrder: Int): List<GithubRepoEntity> {
    val result = arrayListOf<GithubRepoEntity>()
    var currentOrder = maxOrder
    this?.forEach {
        result.add(it.toDbRepository(currentOrder))
        currentOrder++
    }
    return result
}


fun GithubRepo.toRepository(): Repository {
    return Repository(
        this.nodeId ?: "",
        this.name,
        this.description,
        this.htmlUrl,
        this.stargazersCount,
        0,
        false
    )
}

fun Repository.toDbRepository(maxOrder: Int): GithubRepoEntity {
    return GithubRepoEntity(
        0,
        this.remoteId,
        this.name,
        this.description,
        this.reference,
        this.starsCount,
        maxOrder,
        this.isViewed
    )
}

fun GithubRepoEntity.toRepository(): Repository {
    return Repository(
        this.remoteId,
        this.name,
        this.description,
        this.reference,
        this.starsCount,
        this.order,
        this.isViewed
    )
}