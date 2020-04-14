package com.lucasdias.ui_components.bottomnavigation

import android.app.Activity
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lucasdias.ui_components.R
import com.lucasdias.ui_components.bottomnavigation.model.BottomNavigationOption

class BottomNavigation {

    companion object {

        private lateinit var bottomNavigationView: BottomNavigationView

        fun init(
            hostActivity: Activity,
            backgroundColor: Int,
            hostBottomNavigationContainer: FrameLayout,
            bottomNavigationOptionList: ArrayList<BottomNavigationOption>
        ) {
            val view = getInflatedLayout(activity = hostActivity)
            val bottomNavigationView = getBottomNavigationView(view = view)
            updateBottomNavigateView(bottomNavigationView = bottomNavigationView)
            bottomNavigationSetup(
                bottomNavigationView = bottomNavigationView,
                backgroundColor = backgroundColor,
                bottomNavigationOptionList = bottomNavigationOptionList
            )
            clickListenersSetup(
                bottomNavigationView = bottomNavigationView,
                bottomNavigationOptionList = bottomNavigationOptionList
            )

            hostBottomNavigationContainer.addView(view)
        }

        fun getBottomNavigationView(): BottomNavigationView? {
            return if (Companion::bottomNavigationView.isInitialized) bottomNavigationView
            else null
        }

        private fun getBottomNavigationView(view: View): BottomNavigationView {
            val bottomNavigationView =
                view.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
            return bottomNavigationView
        }

        private fun updateBottomNavigateView(bottomNavigationView: BottomNavigationView) {
            Companion.bottomNavigationView = bottomNavigationView
        }

        private fun getInflatedLayout(activity: Activity): View {
            val inflater = activity.layoutInflater
            return inflater.inflate(R.layout.bottom_navigation_layout, null)
        }

        private fun bottomNavigationSetup(
            bottomNavigationView: BottomNavigationView,
            backgroundColor: Int,
            bottomNavigationOptionList: ArrayList<BottomNavigationOption>
        ) {
            val menu = bottomNavigationView.menu
            bottomNavigationView.setBackgroundColor(backgroundColor)
            bottomNavigationOptionList.forEach { bottomNavigationOption ->
                menu.add(
                    Menu.NONE,
                    bottomNavigationOption.id,
                    bottomNavigationOption.order,
                    bottomNavigationOption.title
                ).icon = bottomNavigationOption.icon
            }
        }

        private fun clickListenersSetup(
            bottomNavigationView: BottomNavigationView,
            bottomNavigationOptionList: ArrayList<BottomNavigationOption>
        ) {
            bottomNavigationView.setOnNavigationItemSelectedListener { menuOption ->
                bottomNavigationOptionList.forEach { bottomNavigationOption ->
                    if (bottomNavigationOption.id == menuOption.itemId) {
                        bottomNavigationOption.startFragment.invoke()
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                return@setOnNavigationItemSelectedListener false
            }
        }
    }
}
