package com.lucasdias.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.lucasdias.extensions.bind
import com.lucasdias.search.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchFragment(private val searchClickMethod: (String) -> Unit?) : Fragment() {

    companion object {
        fun newInstance(searchClickMethod: (String) -> Unit?) =
            SearchFragment(searchClickMethod)
    }

    private val viewModel by inject<SearchViewModel>()
    private val adapter: SearchAdapter by inject { parametersOf(searchClickMethod) }
    private val recyclerView by bind<RecyclerView>(R.id.historic_list_search_fragment)
    private val searchButton by bind<Button>(R.id.search_button_search_fragment)
    private val inputTextArea by bind<TextInputEditText>(R.id.input_edit_text_search_fragment)
    private val searchMotionLayout by bind<MotionLayout>(R.id.search_motion_layout)
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchButtonListener()
        initButtonAnimation()
        initHistoricList()
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initButtonAnimation() {
        searchMotionLayout?.transitionToEnd()
    }

    private fun initSearchButtonListener() {
        searchButton?.setOnClickListener {
            val inputText = inputTextArea?.text.toString()
            viewModel.setSearch(inputText)
            searchClickMethod(inputText)
        }
    }

    private fun initHistoricList() {
        val searchHistoric = viewModel.getHistoric()
        adapter.updateSearchTextList(searchHistoric)
    }
}
