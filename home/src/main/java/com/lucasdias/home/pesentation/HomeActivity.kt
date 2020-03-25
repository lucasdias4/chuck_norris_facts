package com.lucasdias.home.pesentation

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.lucasdias.bottomnavigation.BottomNavigation
import com.lucasdias.bottomnavigation.model.BottomNavigationOption
import com.lucasdias.connectivity.Connectivity
import com.lucasdias.extensions.bind
import com.lucasdias.extensions.toast
import com.lucasdias.factcatalog.presentation.FactCatalogFragment
import com.lucasdias.home.R
import com.lucasdias.toolbar.Toolbar
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private val toolbarContainer by bind<FrameLayout>(R.id.toolbar_container_home_activity)
    private val fragmentContainer by bind<FrameLayout>(R.id.fragment_container_home_activity)
    private val bottomNavigationContainer
            by bind<FrameLayout>(R.id.bottom_navigation_container_home_activity)
    private lateinit var connectivity: Connectivity
    private lateinit var snackbar: Snackbar
    private lateinit var snackbarText: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val factCatalogFragment = FactCatalogFragment.newInstance()
        changeFragment(factCatalogFragment)
        initToolbar()
        initBottomNavigation()
        initConnectivityCallback()
        initConnectivitySnackbar()
        initConnectivityObserver()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, fragment)
            .commit()
    }

    private fun initConnectivityCallback() {
        connectivity = Connectivity(application)
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

    private fun initConnectivityObserver() {
        connectivity.observe(this@HomeActivity, Observer { hasNetworkConnectivity ->
            viewModel.mustShowConnectivitySnackbar(hasNetworkConnectivity)
        })

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
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this,
            R.color.green
        ))
        snackbar.setText(getString(R.string.connectivity_on_snackbar))
        snackbar.show()
    }

    private fun showConnectivityOffSnackbar() {
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this,
            R.color.red
        ))
        snackbar.setText(getString(R.string.connectivity_off_snackbar))
        snackbar.show()
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
