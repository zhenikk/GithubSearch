package com.eugens.githubsearch.presentation.navigation

import androidx.fragment.app.Fragment
import com.eugens.githubsearch.presentation.feature.auth.AuthFragment
import com.eugens.githubsearch.presentation.feature.history.HistoryFragment
import com.eugens.githubsearch.presentation.feature.home.HomeFragment
import com.eugens.githubsearch.presentation.feature.search.SearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return HomeFragment.getInstance()
        }
    }
    class HistoryScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return HistoryFragment.getInstance()
        }
    }
    class AuthScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return AuthFragment.getInstance()
        }
    }
    class SearchScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SearchFragment.getInstance()
        }
    }

}