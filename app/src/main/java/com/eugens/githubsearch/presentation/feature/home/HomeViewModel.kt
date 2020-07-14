package com.eugens.githubsearch.presentation.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eugens.githubsearch.domain.usecase.IsAuthorizedUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.navigation.Screens
import com.eugens.githubsearch.presentation.utils.SimpleObserver
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router, private val isAuthorizedUseCase: IsAuthorizedUseCase) :
    ViewModel() {

    var homeEvent = MutableLiveData<Event<String>>()

    fun openSearchScreen() {
        isAuthorizedUseCase.execute(object : SimpleObserver<Boolean>() {
            override fun onNext(t: Boolean) {
                    super.onNext(t)
                if (t) {
                    router.navigateTo(Screens.SearchScreen())
                } else{
                    homeEvent.postValue(Event("Please authenticate first"))
                }
            }
        })
    }

    fun openHistory() {
        router.navigateTo(Screens.HistoryScreen())
    }

    fun openGithubLogin() {
        router.navigateTo(Screens.AuthScreen())

    }
}