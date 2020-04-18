package com.lucasdias.search.presentation

import android.content.Context
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

class SearchFragment : Fragment() {

    companion object {
        private const val EMPTY_STRING = ""
        fun newInstance() = SearchFragment()
    }

    private val viewModel by inject<SearchViewModel>()
    private val adapter: SearchAdapter by inject { parametersOf(userWantsToSearch) }
    private val recyclerView by bind<RecyclerView>(R.id.historic_list_search_fragment)
    private val searchButton by bind<Button>(R.id.search_button_search_fragment)
    private val inputTextArea by bind<TextInputEditText>(R.id.input_edit_text_search_fragment)
    private val searchMotionLayout by bind<MotionLayout>(R.id.motion_layout_fragment_search)
    private val suggestionViewWrapper by bind<ConstraintLayout>(R.id.wrapper_suggestion_search_fragment)
    private val historicViewWrapper by bind<ConstraintLayout>(R.id.wrapper_historic_search_fragment)
    private val firstSuggestionTagView by bind<ViewGroup>(R.id.wrapper_first_tag_search_suggestion)
    private val secondSuggestionTagView by bind<ViewGroup>(R.id.wrapper_second_tag_search_suggestion)
    private val thirdSuggestionTagView by bind<ViewGroup>(R.id.wrapper_third_tag_search_suggestion)
    private val fourthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_fourth_tag_search_suggestion)
    private val fifthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_fifth_tag_search_suggestion)
    private val sixthSuggestionTagView by bind<ViewGroup>(R.id.wrapper_sixth_tag_search_suggestion)
    private val seventhSuggestionTagView by bind<ViewGroup>(R.id.wrapper_seventh_tag_search_suggestion)
    private val eightSuggestionTagView by bind<ViewGroup>(R.id.wrapper_eighth_tag_search_suggestion)
    private val userWantsToSearch = { search: String -> viewModel.userWantsToSearch(search) }
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var fragmentListener: Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListener = context as Listener
    }

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
            viewModel.inputTextKeyboardHandler(
                event = event,
                keyCode = keyCode,
                searchText = searchText.toString()
            )
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
            viewModel.userWantsToSearch(search = searchText.toString())
        }
    }

    private fun doASearch(search: String) {
        viewModel.setSearch(search = search)
        fragmentListener.searchButtonListener(searchText = search)
    }

    private fun initHistoricList() {
        val searchHistoric = viewModel.getHistoric()
        adapter.updateSearchHistoric(searchHistoric = searchHistoric)
    }

    private fun initSuggestionTags(suggestionTags: List<String>?) {
        val tagsViewsList = ArrayList<ViewGroup?>()
        tagsViewsList.add(firstSuggestionTagView)
        tagsViewsList.add(secondSuggestionTagView)
        tagsViewsList.add(thirdSuggestionTagView)
        tagsViewsList.add(fourthSuggestionTagView)
        tagsViewsList.add(fifthSuggestionTagView)
        tagsViewsList.add(sixthSuggestionTagView)
        tagsViewsList.add(seventhSuggestionTagView)
        tagsViewsList.add(eightSuggestionTagView)

        tagsViewsList.forEachIndexed { index, tagView ->
            initSuggestionTag(tagView = tagView, text = suggestionTags?.get(index))
        }
    }

    private fun initSuggestionTag(
        tagView: ViewGroup?,
        text: String?
    ) {
        val tagTextView = tagView?.findViewById<TextView>(R.id.text_search_suggestion_tag)
        tagTextView?.text = text
        tagTextView?.setOnClickListener {
            viewModel.userWantsToSearch(search = text)
        }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            getRandomCategories().observe(viewLifecycleOwner, Observer { suggestionTags ->
                initSuggestionTags(suggestionTags = suggestionTags)
            })
            showSuggestionAndHistoricViews().observe(viewLifecycleOwner, Observer {
                suggestionViewWrapper?.visible()
                historicViewWrapper?.visible()
            })
            doASearch().observe(viewLifecycleOwner, Observer { search ->
                this@SearchFragment.doASearch(search = search)
            })
            searchMustBeLongerThanTwoCharacters().observe(viewLifecycleOwner, Observer {
                activity?.toast(message = resources.getString(R.string.search_with_small_text_alert_search_fragment))
            })
        }
    }

    interface Listener {
        fun searchButtonListener(searchText: String)
    }
}
