package com.lucasdias.factcatalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucasdias.factcatalog.R
import org.koin.android.viewmodel.ext.android.viewModel

class FactCatalogFragment : Fragment() {

    private val viewModel by viewModel<FactCatalogViewModel>()

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
        viewModel.searchFactsBySubject()
    }
}
