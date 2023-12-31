package com.lucascoelho.cstv.features.matches.ui.matchlist

import com.lucascoelho.core.models.Resource
import com.lucascoelho.data.remote.dtos.MatchDto
import com.lucascoelho.data.remote.dtos.toModel
import com.lucascoelho.domain.usecases.IGetMatchesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MatchListViewModelTest {

    @MockK(relaxed = true)
    private lateinit var getMatchesUseCase: IGetMatchesUseCase

    private lateinit var viewModel: MatchListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MatchListViewModel(getMatchesUseCase)
    }

    @Test
    fun `fetchData should emit loading then matches`() = runTest {
        val matches = listOf(
            MatchDto().toModel(),
            MatchDto().toModel(),
        )
        coEvery { getMatchesUseCase(1) } returns flowOf(Resource.success(matches))

        viewModel.fetchData()

        val result = viewModel.matches.take(2).toList()
        assertEquals(result, listOf(Resource.loading(null), Resource.success(matches)))
    }

    @Test
    fun `fetchNextPage should emit loading then matches`() = runTest {
        val matches = listOf(
            MatchDto().toModel(),
            MatchDto().toModel(),
        )
        coEvery { getMatchesUseCase(1) } returns flowOf(Resource.success(matches))

        viewModel.fetchNextPage()

        val result = viewModel.paginationStatus.take(2).toList()
        assertEquals(result, listOf(Resource.loading(null), Resource.success(matches)))
    }
}
