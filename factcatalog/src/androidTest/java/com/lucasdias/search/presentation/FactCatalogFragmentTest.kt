package com.lucasdias.search.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.lucasdias.core_components.connectivity.Connectivity
import com.lucasdias.core_components.request.statushandler.RequestStatus.Success
import com.lucasdias.factcatalog.R
import com.lucasdias.factcatalog.di.FACT_CATALOG_ADAPTER
import com.lucasdias.factcatalog.di.FACT_CATALOG_CONNECTIVITY
import com.lucasdias.factcatalog.di.FACT_CATALOG_VIEW_MODEL
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.usecase.DeleteAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.GetAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import com.lucasdias.factcatalog.presentation.FactCatalogAdapter
import com.lucasdias.factcatalog.presentation.FactCatalogFragment
import com.lucasdias.factcatalog.presentation.FactCatalogViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class FactCatalogFragmentTest : KoinTest {

    private val coroutineContext = Dispatchers.Unconfined
    private val lambda = { _: String -> }
    private val facts = ArrayList<Fact>()

    private val searchFactsBySubjectFromApi: SearchFactsBySubjectFromApi = mockk()
    private val getAllFactsFromDatabase: GetAllFactsFromDatabase = mockk()
    private val deleteAllFactsFromDatabase: DeleteAllFactsFromDatabase = mockk()

    private val showAnErrorScreenLiveData = spyk(MutableLiveData<Unit>())
    private val showAnEmptySearchScreenLiveData = spyk(MutableLiveData<Unit>())
    private val turnOnLoadingLiveData = spyk(MutableLiveData<Unit>())
    private val turnOffLoadingLiveData = spyk(MutableLiveData<Unit>())
    private val allFactsFromDatabase = spyk(MutableLiveData<List<Fact>>())

    private val connectivity: Connectivity = mockk()
    private val adapter = spyk(FactCatalogAdapter(lambda))
    private val mockedViewModel = spyk(
        FactCatalogViewModel(
            getAllFactsFromDatabase,
            searchFactsBySubjectFromApi,
            deleteAllFactsFromDatabase,
            coroutineContext
            )
    )

    @Before
    fun setUp() {
        factListSetup()
        viewModelSetup()
        connectivitySetup()
        koinSetup()
    }

    @After
    fun clearUp() {
        stopKoin()
    }

    @Test
        fun IF_the_catalog_list_is_already_loaded_THAN_the_recycler_view_will_be_displayed_and_the_placeholder_will_be_hidden() {
        coEvery { searchFactsBySubjectFromApi(FILLED_STRING) } returns Success()

        val factory = { FactCatalogFragment.newInstance() }
        launchFragmentInContainer(Bundle(), instantiate = factory)

        runOnUiThread { turnOffLoadingLiveData.value = Unit }

        onView(withId(R.id.recycler_view_fact_catalog_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view_place_holder_fact_catalog_fragment)).check(matches(not((isDisplayed()))))
    }

    @Test
    fun IF_the_catalog_list_it_is_not_already_loaded_THAN_the_recycler_view_will_be_hidden_and_the_placeholder_will_be_displayed() {
        coEvery { searchFactsBySubjectFromApi(FILLED_STRING) } returns Success()

        val factory = { FactCatalogFragment.newInstance() }
        launchFragmentInContainer(Bundle(), instantiate = factory)

        runOnUiThread { turnOnLoadingLiveData.value = Unit }

        onView(withId(R.id.recycler_view_fact_catalog_fragment)).check(matches(not(isDisplayed())))
        onView(withId(R.id.recycler_view_place_holder_fact_catalog_fragment)).check(matches((isDisplayed())))
    }

    private fun factListSetup() {
        val emptyList = mutableListOf<String>() as List<String>
        val fact = Fact(FILLED_STRING, FILLED_STRING, FILLED_STRING, emptyList)
        facts.add(fact)
        facts.add(fact)
        facts.add(fact)
        facts.add(fact)
    }

    private fun connectivitySetup() {
        every { connectivity.observe(any(), any()) } just Runs
    }

    private fun viewModelSetup() {
        mockedViewModel._showAnErrorScreen = showAnErrorScreenLiveData
        mockedViewModel._showAnEmptySearchScreen = showAnEmptySearchScreenLiveData
        mockedViewModel._turnOnLoading = turnOnLoadingLiveData
        mockedViewModel._turnOffLoading = turnOffLoadingLiveData

        every { mockedViewModel.deleteAllFacts() } just Runs
        every { mockedViewModel.searchFactsBySubject(any()) } just Runs
        every { mockedViewModel.updateConnectivityStatus(any()) } just Runs
        every { getAllFactsFromDatabase(any()) } returns allFactsFromDatabase
        every { deleteAllFactsFromDatabase(any()) } just Runs
    }

    private fun koinSetup() {
        val moduleList = mutableListOf<Module>()
        val viewModelModule = module { viewModel(named(FACT_CATALOG_VIEW_MODEL)) { mockedViewModel } }
        val adapterModule = module { factory(named(FACT_CATALOG_ADAPTER)) { adapter } }
        val connectivityModule =
            module { factory(named(FACT_CATALOG_CONNECTIVITY)) { connectivity } }

        moduleList.add(viewModelModule)
        moduleList.add(adapterModule)
        moduleList.add(connectivityModule)

        startKoin {
            modules(moduleList)
        }
    }

    private companion object {
        const val FILLED_STRING = "ABCD"
    }
}
