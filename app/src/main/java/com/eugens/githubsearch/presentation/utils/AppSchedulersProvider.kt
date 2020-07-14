package com.eugens.githubsearch.presentation.utils

import com.eugens.githubsearch.domain.executor.SchedulersProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class AppSchedulersProvider : SchedulersProvider {
    override fun uiThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun backgroundThread(): Scheduler {
        return Schedulers.io()
    }
}