package com.eugens.githubsearch.presentation.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.eugens.githubsearch.R
import com.eugens.githubsearch.presentation.navigation.Screens
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class BaseActivity : FragmentActivity() {
    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.navigateTo(Screens.HomeScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private val navigator: Navigator =
        SupportAppNavigator(this, R.id.container)

}