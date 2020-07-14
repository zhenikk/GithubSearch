package com.eugens.githubsearch.presentation.feature.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eugens.githubsearch.domain.model.LoginParams
import com.eugens.githubsearch.domain.usecase.AuthUseCase
import com.eugens.githubsearch.presentation.base.Event
import com.eugens.githubsearch.presentation.navigation.Screens
import com.eugens.githubsearch.presentation.utils.SimpleObserver
import ru.terrakok.cicerone.Router

class AuthViewModel(val router: Router, private val authUseCase: AuthUseCase) : ViewModel() {

    val loginLiveData = MutableLiveData<String>()
    val passLiveData = MutableLiveData<String>()
    val loginEvent = MutableLiveData<Event<LoginEvent>>()

    fun authorize() {
        val login = loginLiveData.value
        val pass = passLiveData.value

        if (login.isNullOrEmpty() || pass.isNullOrEmpty()) {
            loginEvent.postValue(Event(LoginEvent.LoginError("Please fill login and pass")))
        } else {
            authUseCase.execute(object : SimpleObserver<Boolean>() {
                override fun onNext(t: Boolean) {
                    super.onNext(t)
                    loginEvent.postValue(Event(LoginEvent.LoginSuccess))
                    router.replaceScreen(Screens.SearchScreen())
                }

                override fun onError(exception: Throwable) {
                    super.onError(exception)
                    loginEvent.postValue(Event(LoginEvent.LoginError(exception.message)))

                }
            }, LoginParams(login, pass))
        }
    }

    fun navigateBack() {
        router.backTo(Screens.HomeScreen())
    }

    sealed class LoginEvent {
        object LoginSuccess : LoginEvent()
        class LoginError(val message: String?) : LoginEvent()
    }
}