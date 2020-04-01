package chucknorrisfacts

import androidx.lifecycle.MutableLiveData
import com.lucasdias.search.domain.sealedclass.Error
import com.lucasdias.search.domain.sealedclass.Success
import com.lucasdias.search.domain.usecase.GetRandomCategoriesFromDatabase
import com.lucasdias.search.domain.usecase.GetSearchHistoric
import com.lucasdias.search.domain.usecase.IsCategoryCacheEmpty
import com.lucasdias.search.domain.usecase.SearchCategoriesFromApi
import com.lucasdias.search.domain.usecase.SetSearchHistoric
import com.lucasdias.search.presentation.SearchViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchViewModel {

    private val coroutineContext = Dispatchers.Unconfined
    private val getSearchHistoric: GetSearchHistoric = mockk()
    private val setSearchHistoric: SetSearchHistoric = mockk()
    private val searchCategoriesFromApi: SearchCategoriesFromApi = mockk()
    private val getRandomCategoriesFromDatabase: GetRandomCategoriesFromDatabase = mockk()
    private val isCategoryCacheEmpty: IsCategoryCacheEmpty = mockk()
    private val viewModel = spyk(
        SearchViewModel(
            getSearchHistoric = getSearchHistoric,
            setSearchHistoric = setSearchHistoric,
            searchCategoriesFromApi = searchCategoriesFromApi,
            getRandomCategoriesFromDatabase = getRandomCategoriesFromDatabase,
            isCategoryCacheEmpty = isCategoryCacheEmpty
        )
    )

    private var errorToLoadCategories: MutableLiveData<Unit> = mockk()
    private var randomCategoriesLiveData: MutableLiveData<List<String>?> = mockk()
    private var showSuggestionAndHistoricViews: MutableLiveData<Unit> = mockk()

    @Before
    fun setUp() {
        viewModel.coroutineContext = coroutineContext
    }

    @Test
    fun `IF the application needs to display a list of categories THAN always calls the isCategoryCacheEmpty method to find out if the application already has a list in the cache`() {
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns true

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Error

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 1) {
            viewModel.isCategoryCacheEmpty()
        }
    }

    @Test
    fun `IF the category cache is empty THEN make a request by calling the searchCategoriesFromApi method`() {
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns true

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Error

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 1) {
            viewModel.searchCategoriesFromApi()
        }
    }

    @Test
    fun `IF the category cache is not empty THEN do not make a request by calling the searchCategoriesFromApi method`() {
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns false

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Error

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 0) {
            viewModel.searchCategoriesFromApi()
        }
    }

    @Test
    fun `IF the application needs to display a list of categories THAN always calls the howSuggestionAndHistoricViews livedata to show the view`() {
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns true

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Error

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 1) {
            showSuggestionAndHistoricViews.postValue(Unit)
        }
    }

    @Test
    fun `IF the application makes the request to get categories with a successful state or already has categories in the cache THEN calls the getRandomCategoriesFromDatabase method`() {
        val categories = ArrayList<String>()
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns true

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Success

        coEvery {
            viewModel.getRandomCategoriesFromDatabase()
        } returns categories

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 1) {
            viewModel.getRandomCategoriesFromDatabase()
        }
    }

    @Test
    fun `IF the getRandomCategoriesFromDatabase method returns a non-empty list THEN call randomCategoriesLiveData with that list`() {
        val categories = ArrayList<String>()
        categories.add(FILLED_STRING)
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns true

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Success

        coEvery {
            viewModel.getRandomCategoriesFromDatabase()
        } returns categories

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 1) {
            randomCategoriesLiveData.postValue(categories)
        }
    }

    @Test
    fun `IF the getRandomCategoriesFromDatabase method returns am empty list THEN do not call randomCategoriesLiveData with that list`() {
        val categories = ArrayList<String>()
        searchCategoriesMethodSetup()

        coEvery {
            viewModel.isCategoryCacheEmpty()
        } returns true

        coEvery {
            viewModel.searchCategoriesFromApi()
        } returns Success

        coEvery {
            viewModel.getRandomCategoriesFromDatabase()
        } returns categories

        runBlocking {
            viewModel.searchCategories()
        }

        coVerify(exactly = 0) {
            randomCategoriesLiveData.postValue(categories)
        }
    }

    private fun searchCategoriesMethodSetup() {
        viewModel.errorToLoadCategories = errorToLoadCategories
        viewModel.randomCategoriesLiveData = randomCategoriesLiveData
        viewModel.showSuggestionAndHistoricViews = showSuggestionAndHistoricViews

        coEvery {
            errorToLoadCategories.postValue(Unit)
        } just Runs

        coEvery {
            randomCategoriesLiveData.postValue(any())
        } just Runs

        coEvery {
            showSuggestionAndHistoricViews.postValue(Unit)
        } just Runs
    }

    private companion object {
        const val FILLED_STRING = "ABCD"
    }
}
