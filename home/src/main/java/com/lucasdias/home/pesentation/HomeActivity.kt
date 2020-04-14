package com.lucasdias.home.pesentation

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.lucasdias.core_components.connectivity.Connectivity
import com.lucasdias.extensions.bind
import com.lucasdias.factcatalog.presentation.FactCatalogFragment
import com.lucasdias.home.R
import com.lucasdias.home.di.HOME_CONNECTIVITY
import com.lucasdias.search.presentation.SearchFragment
import com.lucasdias.ui_components.bottomnavigation.BottomNavigation
import com.lucasdias.ui_components.bottomnavigation.model.BottomNavigationOption
import com.lucasdias.ui_components.toolbar.Toolbar
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeActivity : AppCompatActivity(), SearchFragment.Listener {

    private val viewModel by viewModel<HomeViewModel>()
    private val connectivity by inject<Connectivity>(named(HOME_CONNECTIVITY))
    private val toolbarContainer by bind<FrameLayout>(R.id.toolbar_container_home_activity)
    private val fragmentContainer by bind<FrameLayout>(R.id.fragment_container_home_activity)
    private val bottomNavigationContainer
            by bind<FrameLayout>(R.id.bottom_navigation_container_home_activity)
    private lateinit var searchFragment: SearchFragment
    private lateinit var factCatalogFragment: FactCatalogFragment
    private lateinit var snackbar: Snackbar
    private lateinit var snackbarText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initToolbar()
        initBottomNavigation()
        initFirstTimeObservers()
        initConnectivitySnackbar()
        initSnackbarObserver()
        initConnectivityObserver()
        viewModel.verifyIfItIsTheUsersFirstTimeOnCache()
    }

    override fun searchButtonListener(searchText: String) {
        selectBottomNavigationOptionProgrammatically(bottomNavigationOptionId = BottomNavigationEnum.RESULT.id)
        factCatalogFragment.updateSearch(searchText = searchText)
    }

    private fun startFactCatalogFragment() {
        factCatalogFragment = FactCatalogFragment.newInstance()
        changeFragment(fragment = factCatalogFragment)
        initFactCatalogFragmentObservers(factCatalogFragment = factCatalogFragment)
    }

    private fun startSearchFragment() {
        searchFragment = SearchFragment.newInstance()
        changeFragment(fragment = searchFragment)
        initSearchFragmentObservers(searchFragment = searchFragment)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, fragment)
            .commit()
    }

    private fun initFirstTimeObservers() {
        viewModel.apply {
            isTheUserFirstTime().observe(this@HomeActivity, Observer {
                val userFirstTimeFragment = UserFirstTimeFragment.newInstance()
                changeFragment(fragment = userFirstTimeFragment)
                setThatIsNotTheUsersFirstTime()
            })
            isNotTheUserFirstTime().observe(this@HomeActivity, Observer {
                startFactCatalogFragment()
            })
        }
    }

    private fun initFactCatalogFragmentObservers(factCatalogFragment: FactCatalogFragment) {
        factCatalogFragment.apply {
            showAnErrorScreenLiveData().observe(this@HomeActivity, Observer {
                val errorFragment = ErrorFragment.newInstance()
                changeFragment(fragment = errorFragment)
            })
            showAnEmptySearchLiveData().observe(this@HomeActivity, Observer {
                val emptySearchFragment = EmptySearchFragment.newInstance()
                changeFragment(fragment = emptySearchFragment)
            })
        }
    }

    private fun initSearchFragmentObservers(searchFragment: SearchFragment) {
        searchFragment.apply {
            errorToLoadSearchCategories().observe(this@HomeActivity, Observer {
                val errorFragment = ErrorFragment.newInstance()
                changeFragment(fragment = errorFragment)
            })
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(this@HomeActivity, Observer { hasNetworkConnectivity ->
            viewModel.mustShowConnectivitySnackbar(hasNetworkConnectivity = hasNetworkConnectivity)
        })
    }

    private fun initConnectivitySnackbar() {
        snackbar =
            Snackbar.make(
                fragmentContainer,
                getString(R.string.connectivity_on_snackbar),
                Snackbar.LENGTH_INDEFINITE
            )
        snackbarText = snackbar.view.findViewById(R.id.snackbar_text)
        snackbarText.textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private fun initSnackbarObserver() {
        viewModel.apply {
            showConnectivityOnSnackbar().observe(this@HomeActivity, Observer {
                this@HomeActivity.showConnectivityOnSnackbar()
            })

            showConnectivityOffSnackbar().observe(this@HomeActivity, Observer {
                this@HomeActivity.showConnectivityOffSnackbar()
            })
        }
    }

    private fun showConnectivityOnSnackbar() {
        snackbar.duration = Snackbar.LENGTH_LONG
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                this@HomeActivity,
                R.color.green
            )
        )
        snackbar.setText(getString(R.string.connectivity_on_snackbar))
        snackbar.show()
    }

    private fun showConnectivityOffSnackbar() {
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                this@HomeActivity,
                R.color.red
            )
        )
        snackbar.setText(getString(R.string.connectivity_off_snackbar))
        snackbar.show()
    }

    private fun initToolbar() {
        val toolbarIcon = resources.getDrawable(R.drawable.ic_toolbar, null)
        val toolbarTitle = resources.getString(R.string.toolbar_title_home)
        val toolbarTitleStyle = R.style.DefaultToolbarTitle
        val toolbarColor = R.color.lightWhite
        Toolbar.initializeToolbar(
            activity = this@HomeActivity,
            toolbarContainer = toolbarContainer,
            supportActionBar = supportActionBar
        )
        Toolbar.setToolbarTitleStyle(
            activity = this@HomeActivity,
            toolbarTitleStyle = toolbarTitleStyle
        )
        Toolbar.setToolbarColor(activity = this@HomeActivity, toolbarColor = toolbarColor)
        Toolbar.setToolbarIcon(activity = this@HomeActivity, icon = toolbarIcon)
        Toolbar.setToolbarTitle(activity = this@HomeActivity, title = toolbarTitle)
    }

    private fun initBottomNavigation() {
        val menuFirstPosition = 1
        val menuSecondPosition = 2

        val bottomNavigationBackgroundColor = ContextCompat.getColor(
            this@HomeActivity,
            R.color.darkBlack
        )

        val resultOptionTitle = resources.getString(R.string.bottom_navigation_facts)
        val resultOptionClickAction = { startFactCatalogFragment() }
        val resultOptionIcon = resources.getDrawable(
            R.drawable.bottom_navigation_facts_icon,
            null
        )

        val searchOptionTitle = resources.getString(R.string.bottom_navigation_search)
        val searchOptionClickAction = { startSearchFragment() }
        val searchOptionIcon = resources.getDrawable(
            R.drawable.bottom_navigation_search_icon,
            null
        )

        val bottomNavigationOptionList = ArrayList<BottomNavigationOption>()

        bottomNavigationOptionList.add(
            BottomNavigationOption(
                id = BottomNavigationEnum.RESULT.id,
                title = resultOptionTitle,
                icon = resultOptionIcon,
                order = menuFirstPosition,
                startFragment = resultOptionClickAction
            )
        )
        bottomNavigationOptionList.add(
            BottomNavigationOption(
                id = BottomNavigationEnum.SEARCH.id,
                title = searchOptionTitle,
                icon = searchOptionIcon,
                order = menuSecondPosition,
                startFragment = searchOptionClickAction
            )
        )

        BottomNavigation.init(
            hostActivity = this@HomeActivity,
            backgroundColor = bottomNavigationBackgroundColor,
            hostBottomNavigationContainer = bottomNavigationContainer,
            bottomNavigationOptionList = bottomNavigationOptionList
        )
    }

    private fun selectBottomNavigationOptionProgrammatically(bottomNavigationOptionId: Int) {
        val bottomNavigationView: BottomNavigationView? = BottomNavigation.getBottomNavigationView()
        bottomNavigationView?.selectedItemId = bottomNavigationOptionId
    }

    private enum class BottomNavigationEnum(val id: Int) {
        RESULT(id = 1),
        SEARCH(id = 2)
    }
}
