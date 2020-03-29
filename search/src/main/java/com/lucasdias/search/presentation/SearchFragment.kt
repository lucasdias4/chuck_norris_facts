package com.lucasdias.search.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
        private const val FIRST_SUGGESTION = 0
        private const val SECOND_SUGGESTION = 1
        private const val THIRD_SUGGESTION = 2
        private const val FOURTH_SUGGESTION = 3
        private const val FIFTH_SUGGESTION = 4
        private const val SIXTH_SUGGESTION = 5
        private const val SEVENTH_SUGGESTION = 6
        private const val EIGHTH_SUGGESTION = 7

        fun newInstance(searchClickMethod: (String) -> Unit?) =
            SearchFragment(searchClickMethod)
    }

    private val viewModel by inject<SearchViewModel>()
    private val adapter: SearchAdapter by inject { parametersOf(searchClickMethod) }
    private val recyclerView by bind<RecyclerView>(R.id.historic_list_search_fragment)
    private val searchButton by bind<Button>(R.id.search_button_search_fragment)
    private val inputTextArea by bind<TextInputEditText>(R.id.input_edit_text_search_fragment)
    private val searchMotionLayout by bind<MotionLayout>(R.id.motion_layout_fragment_search)
    private val firstSuggestionTagView by bind<ViewGroup>(R.id.wrapper_first_tag_search_suggestion)
    private val secondSuggestionTagView by bind<ViewGroup>(R.id.wrapper_second_tag_search_suggestion)
    private val thirdSuggestionTagView by bind<ViewGroup>(R.id.wrapper_third_tag_search_suggestion)
    private val fourthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_fourth_tag_search_suggestion)
    private val fifthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_fifth_tag_search_suggestion)
    private val sixthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_sixth_tag_search_suggestion)
    private val seventhSuggestionTagView by bind<ViewGroup>(R.id.wrapper_seventh_tag_search_suggestion)
    private val eightSuggestionTagView by bind<ViewGroup>(R.id.wrapper_eighth_tag_search_suggestion)
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
        initTextInput()
        initViewModelObservers()
        viewModel.searchCategories()
    }

    fun errorToLoadSearchCategories() = viewModel.errorToLoadCategories()

    private fun initTextInput() {
        inputTextArea?.setOnKeyListener { _, keyCode, event ->
            val userHitEnterButton =
                (event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)
            if (userHitEnterButton) doASearch(inputTextArea)
            return@setOnKeyListener true
        }
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
            doASearch(inputTextArea)
        }
    }

    private fun doASearch(inputTextArea: TextInputEditText?) {
        val inputText = inputTextArea?.text.toString()
        viewModel.setSearch(inputText)
        searchClickMethod(inputText)
    }

    private fun initHistoricList() {
        val searchHistoric = viewModel.getHistoric()
        adapter.updateSearchTextList(searchHistoric)
    }

    private fun initSuggestionTags(suggestionTags: List<String>?) {
        initSuggestionTag(firstSuggestionTagView, suggestionTags?.get(FIRST_SUGGESTION))
        initSuggestionTag(secondSuggestionTagView, suggestionTags?.get(SECOND_SUGGESTION))
        initSuggestionTag(thirdSuggestionTagView, suggestionTags?.get(THIRD_SUGGESTION))
        initSuggestionTag(fourthSuggestionTagView, suggestionTags?.get(FOURTH_SUGGESTION))
        initSuggestionTag(fifthSuggestionTagView, suggestionTags?.get(FIFTH_SUGGESTION))
        initSuggestionTag(sixthSuggestionTagView, suggestionTags?.get(SIXTH_SUGGESTION))
        initSuggestionTag(seventhSuggestionTagView, suggestionTags?.get(SEVENTH_SUGGESTION))
        initSuggestionTag(eightSuggestionTagView, suggestionTags?.get(EIGHTH_SUGGESTION))
    }

    private fun initSuggestionTag(
        suggestionTagView: ViewGroup?,
        text: String?
    ) {
        val tagTextView = suggestionTagView?.findViewById<TextView>(R.id.text_search_suggestion_tag)
        tagTextView?.text = text
        tagTextView?.setOnClickListener {
            text?.let {
                viewModel.setSearch(text)
                searchClickMethod(text)
            }
        }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            getRandomCategories().observe(this@SearchFragment, Observer { suggestionTags ->
                initSuggestionTags(suggestionTags)
            })
        }
    }
}
