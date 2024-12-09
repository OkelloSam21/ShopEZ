package com.samuelokello.shopspot.ui.search

import com.samuelokello.shopspot.domain.Product
import com.samuelokello.shopspot.fake.FakeDataSource
import com.samuelokello.shopspot.fake.FakeProductRepository
import com.samuelokello.shopspot.rule.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var repository: FakeProductRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        repository = FakeProductRepository()
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun searchProducts_whenSuccessful_updatesStateToSuccess() = runTest {
        // Keep track of emitted states
        val states = mutableListOf<SearchUiState>()

        // Start collecting states
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.searchUiState.collect { state ->
                states.add(state)
            }
        }

        // Initial state should be Loading
        assertEquals(SearchUiState.Loading, states.last())

        // Perform search
        viewModel.search("s")

        // Advance time to allow Flow to complete
        advanceUntilIdle()

        // Verify final state
        assertTrue(
            "Expected Success state but was ${states.last()}. All states: $states",
            states.last() is SearchUiState.Success
        )

        // Optional: Verify the content of Success state
        val finalState = states.last() as SearchUiState.Success
        assertEquals(FakeDataSource.fakeProductsList.first(), finalState.products)

        // Cleanup
        job.cancel()
    }

    @Test
    fun searchProducts_withEmptyQuery_returnsEmptyList() = runTest {
        viewModel.search("")
        advanceUntilIdle()

        assertTrue(viewModel.searchUiState.value is SearchUiState.Success)
        assertEquals(
            emptyList<Product>(),
            (viewModel.searchUiState.value as SearchUiState.Success).products
        )
    }
}