package com.eugens.githubsearch.domain.executor

import io.reactivex.rxjava3.core.Scheduler

interface SchedulersProvider {
    fun uiThread(): Scheduler
    fun backgroundThread(): Scheduler
}