package com.lucasdias.factcatalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.lucasdias.connectivity.Connectivity
import com.lucasdias.extensions.bind
import com.lucasdias.extensions.gone
import com.lucasdias.extensions.visible
import com.lucasdias.factcatalog.R
import com.lucasdias.factcatalog.di.FACT_CATALOG_CONNECTIVITY
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FactCatalogFragment : Fragment() {

    companion object {
        fun newInstance() = FactCatalogFragment()
    }

    private val viewModel by viewModel<FactCatalogViewModel>()
    private val adapter by inject<FactCatalogAdapter>()
    private val connectivity by inject<Connectivity>(named(FACT_CATALOG_CONNECTIVITY))
    private val swipeRefresh by bind<SwipeRefreshLayout>(R.id.swipe_refresh_fact_catalag_fragment)
    private val recyclerView by bind<RecyclerView>(R.id.recycler_view_fact_catalog_fragment)
    private val recyclerViewPlaceHolder by bind<ShimmerFrameLayout>(R.id.recycler_view_place_holder_fact_catalog_fragment)
    private lateinit var layoutManager: LinearLayoutManager

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
        initSwipeRefresh()
        initViewModelObservers()
        initConnectivityObserver()
    }

    fun showAnErrorScreenLiveData() = viewModel.showAnErrorScreenLiveData()
    fun showAnEmptySearchLiveData() = viewModel.showAnEmptySearchScreenLiveData()

    fun updateSearch(searchText: String) {
        viewModel.apply {
            deleteAllFacts()
            setActualSearchTextOnCache(searchText = searchText)
            searchFactsBySubject(subject = searchText)
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initSwipeRefresh() {
        swipeRefresh?.setOnRefreshListener {
            val searchText = viewModel.getActualSearchTextFromCache()
            updateSearch(searchText = searchText)
            swipeRefresh?.isRefreshing = false
        }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            updateFactsLiveData().observe(this@FactCatalogFragment, Observer { facts ->
                adapter.updateFactCatalog(facts)
            })
            turnOnLoadingLiveData().observe(this@FactCatalogFragment, Observer {
                recyclerView?.gone()
                recyclerViewPlaceHolder?.visible()
            })
            turnOffLoadingLiveData().observe(this@FactCatalogFragment, Observer {
                recyclerView?.visible()
                recyclerViewPlaceHolder?.gone()
            })
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(this@FactCatalogFragment, Observer { hasNetworkConnectivity ->
            viewModel.updateConnectivityStatus(hasNetworkConnectivity)
        })
    }
}
