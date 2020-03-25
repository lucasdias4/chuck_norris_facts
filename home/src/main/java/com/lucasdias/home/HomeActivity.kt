package com.lucasdias.home

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lucasdias.bottomnavigation.BottomNavigation
import com.lucasdias.bottomnavigation.model.BottomNavigationOption
import com.lucasdias.extensions.bind
import com.lucasdias.extensions.toast
import com.lucasdias.factcatalog.presentation.FactCatalogFragment
import com.lucasdias.toolbar.Toolbar

class HomeActivity : AppCompatActivity() {

    private val toolbarContainer by bind<FrameLayout>(R.id.toolbar_container_home_activity)
    private val fragmentContainer by bind<FrameLayout>(R.id.fragment_container_home_activity)
    private val bottomNavigationContainer
            by bind<FrameLayout>(R.id.bottom_navigation_container_home_activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val factCatalogFragment = FactCatalogFragment.newInstance()
        changeFragment(factCatalogFragment)
        initToolbar()
        initBottomNavigation()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, fragment)
            .commit()
    }

    private fun initToolbar() {
        val toolbarIcon = resources.getDrawable(R.drawable.ic_toolbar, null)
        val toolbarTitle = resources.getString(R.string.toolbar_title_home)
        val toolbarTitleStyle = R.style.DefaultToolbarTitle
        val toolbarColor = R.color.lightWhite
        Toolbar.initializeToolbar(this@HomeActivity, toolbarContainer, supportActionBar)
        Toolbar.setToolbarColor(this@HomeActivity, toolbarColor)
        Toolbar.setToolbarIcon(this@HomeActivity, toolbarIcon)
        Toolbar.setToolbarTitle(this@HomeActivity, toolbarTitle)
        Toolbar.setToolbarTitleStyle(this@HomeActivity, toolbarTitleStyle)
    }

    private fun initBottomNavigation() {
        val menuFirstPostion = 1
        val menuSecondPostion = 2

        val bottomNavigationBackgroundColor = ContextCompat.getColor(
            this@HomeActivity,
            R.color.darkBlack
        )

        val resultOptionTitle = resources.getString(R.string.bottom_navigation_facts)
        val resultOptionClickAction = {
            toast("facts")
        }
        val resultOptionIcon = resources.getDrawable(
            R.drawable.bottom_navigation_facts_icon,
            null
        )

        val searchOptionTitle = resources.getString(R.string.bottom_navigation_search)
        val searchOptionClickAction = {
            toast("search")
        }
        val searchOptionIcon = resources.getDrawable(
            R.drawable.bottom_navigation_search_icon,
            null
        )

        val bottomNavigationOptionList = ArrayList<BottomNavigationOption>()

        bottomNavigationOptionList.add(
            BottomNavigationOption(
                BottomNavigationEnum.RESULT.id,
                resultOptionTitle,
                resultOptionIcon,
                menuFirstPostion,
                resultOptionClickAction
            )
        )
        bottomNavigationOptionList.add(
            BottomNavigationOption(
                BottomNavigationEnum.SEARCH.id,
                searchOptionTitle,
                searchOptionIcon,
                menuSecondPostion,
                searchOptionClickAction
            )
        )

        BottomNavigation.init(
            this@HomeActivity,
            bottomNavigationBackgroundColor,
            bottomNavigationContainer,
            bottomNavigationOptionList
        )
    }

    private enum class BottomNavigationEnum(val id: Int) {
        RESULT(1),
        SEARCH(2)
    }
}
