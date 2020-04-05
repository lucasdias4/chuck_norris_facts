package com.lucasdias.factcatalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.lucasdias.connectivity.Connectivity
import com.lucasdias.extensions.bind
import com.lucasdias.extensions.gone
import com.lucasdias.extensions.visible
import com.lucasdias.factcatalog.R
import com.lucasdias.factcatalog.di.FACT_CATALOG_ADAPTER
import com.lucasdias.factcatalog.di.FACT_CATALOG_CONNECTIVITY
import com.lucasdias.factcatalog.di.FACT_CATALOG_VIEW_MODEL
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class FactCatalogFragment : Fragment() {

    companion object {
        fun newInstance() = FactCatalogFragment()
    }

    private val viewModel by viewModel<FactCatalogViewModel>(named(FACT_CATALOG_VIEW_MODEL))
    private val adapter by inject<FactCatalogAdapter>(named(FACT_CATALOG_ADAPTER)) { parametersOf(shareUrlMethod) }
    private val connectivity by inject<Connectivity>(named(FACT_CATALOG_CONNECTIVITY))
    private val recyclerView by bind<RecyclerView>(R.id.recycler_view_fact_catalog_fragment)
    private val recyclerViewPlaceHolder by bind<ShimmerFrameLayout>(R.id.recycler_view_place_holder_fact_catalog_fragment)
    private val shareUrlMethod = { url: String -> shareUrl(url) }
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
        initViewModelObservers()
        initConnectivityObserver()
    }

    fun showAnErrorScreenLiveData() = viewModel.showAnErrorScreenLiveData()
    fun showAnEmptySearchLiveData() = viewModel.showAnEmptySearchScreenLiveData()

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
            updateFactsLiveData().observe(viewLifecycleOwner, Observer { facts ->
                adapter.updateFactCatalog(facts = facts)
            })
            turnOnLoadingLiveData().observe(viewLifecycleOwner, Observer {
                recyclerView?.gone()
                recyclerViewPlaceHolder?.visible()
            })
            turnOffLoadingLiveData().observe(viewLifecycleOwner, Observer {
                recyclerView?.visible()
                recyclerViewPlaceHolder?.gone()
            })
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(viewLifecycleOwner, Observer { hasNetworkConnectivity ->
            viewModel.updateConnectivityStatus(hasNetworkConnectivity = hasNetworkConnectivity)
        })
    }

    private fun shareUrl(url: String?) {
        activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setType(resources.getString(R.string.share_url_share_compact_type))
                .setChooserTitle(R.string.share_url_message)
                .setText(url)
                .startChooser()
        }
    }
}
