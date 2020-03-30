package com.lucasdias.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.lucasdias.extensions.bind
import com.lucasdias.extensions.toast
import com.lucasdias.extensions.visible
import com.lucasdias.search.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchFragment(private val searchClickMethod: (String) -> Unit?) : Fragment() {

    companion object {
        private const val EMPTY_STRING = ""
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
    private val adapter: SearchAdapter by inject { parametersOf(userWantsToSearch) }
    private val recyclerView by bind<RecyclerView>(R.id.historic_list_search_fragment)
    private val searchButton by bind<Button>(R.id.search_button_search_fragment)
    private val inputTextArea by bind<TextInputEditText>(R.id.input_edit_text_search_fragment)
    private val searchMotionLayout by bind<MotionLayout>(R.id.motion_layout_fragment_search)
    private val suggestion_view_wrapper by bind<ConstraintLayout>(R.id.wrapper_suggestion_search_fragment)
    private val historic_view_wrapper by bind<ConstraintLayout>(R.id.wrapper_historic_search_fragment)
    private val firstSuggestionTagView by bind<ViewGroup>(R.id.wrapper_first_tag_search_suggestion)
    private val secondSuggestionTagView by bind<ViewGroup>(R.id.wrapper_second_tag_search_suggestion)
    private val thirdSuggestionTagView by bind<ViewGroup>(R.id.wrapper_third_tag_search_suggestion)
    private val fourthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_fourth_tag_search_suggestion)
    private val fifthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_fifth_tag_search_suggestion)
    private val sixthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_sixth_tag_search_suggestion)
    private val seventhSuggestionTagView by bind<ViewGroup>(R.id.wrapper_seventh_tag_search_suggestion)
    private val eightSuggestionTagView by bind<ViewGroup>(R.id.wrapper_eighth_tag_search_suggestion)
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private val userWantsToSearch = { search: String -> viewModel.userWantsToSearch(search) }

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
            val searchText = inputTextArea?.text ?: EMPTY_STRING
            viewModel.inputTextKeyboardHandler(event, keyCode, searchText.toString())
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
            val searchText = inputTextArea?.text ?: EMPTY_STRING
            viewModel.userWantsToSearch(searchText.toString())
        }
    }

    private fun doASearch(search: String) {
        viewModel.setSearch(search)
        searchClickMethod(search)
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
            viewModel.userWantsToSearch(text)
        }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            getRandomCategories().observe(this@SearchFragment, Observer { suggestionTags ->
                initSuggestionTags(suggestionTags)
            })
            showSuggestionAndHistoricViews().observe(this@SearchFragment, Observer {
                suggestion_view_wrapper?.visible()
                historic_view_wrapper?.visible()
            })
            doASearch().observe(this@SearchFragment, Observer { search ->
                this@SearchFragment.doASearch(search)
            })
            searchMustBeLongerThanTwoCharacters().observe(this@SearchFragment, Observer { search ->
                activity?.toast(resources.getString(R.string.search_with_small_text_alert_search_fragment))
            })
        }
    }
}
