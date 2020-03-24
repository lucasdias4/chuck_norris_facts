package com.lucasdias.home

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lucasdias.extensions.bind
import com.lucasdias.factcatalog.presentation.FactCatalogFragment

class HomeActivity : AppCompatActivity() {

    private val fragmentContainer by bind<FrameLayout>(R.id.fragment_container_home_activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val factCatalogFragment = FactCatalogFragment.newInstance()
        changeFragment(factCatalogFragment)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, fragment)
            .commit()
    }
}
