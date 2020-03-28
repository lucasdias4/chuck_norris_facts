package com.lucasdias.factcatalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasdias.connectivity.Connectivity
import com.lucasdias.extensions.bind
import com.lucasdias.extensions.toast
import com.lucasdias.factcatalog.R
import com.lucasdias.factcatalog.di.FACT_CATALOG_CONNECTIVITY
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FactCatalogFragment : Fragment() {

    private val viewModel by viewModel<FactCatalogViewModel>()
    private val adapter by inject<FactCatalogAdapter>()
    private val connectivity by inject<Connectivity>(named(FACT_CATALOG_CONNECTIVITY))
    private val recyclerView by bind<RecyclerView>(R.id.recycler_view_fact_catalog_fragment)
    private lateinit var layoutManager: LinearLayoutManager

    companion object {
        fun newInstance() = FactCatalogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModelObservers()
        initConnectivityObserver()
    }

    fun updateSearch(searchText: String) {
        viewModel.apply {
            deleteAllFacts()
            searchFactsBySubject(subject = searchText)
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            updateFactsLiveData().observe(this@FactCatalogFragment, Observer { facts ->
                adapter.updateFactCatalog(facts)
            })
            showAnErrorScreenLiveData().observe(this@FactCatalogFragment, Observer {
                context?.toast("showAnErrorScreenLiveData")
            })
            showAnEmptySearchScreenLiveData().observe(this@FactCatalogFragment, Observer {
                context?.toast("showAnEmptySearchScreenLiveData")
            })
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(this@FactCatalogFragment, Observer { hasNetworkConnectivity ->
            viewModel.updateConnectivityStatus(hasNetworkConnectivity)
        })
    }
}
