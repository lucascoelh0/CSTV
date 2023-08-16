package com.lucascoelho.cstv.features.matches.ui.matchdetails

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.lucascoelho.core.constants.EMPTY
import com.lucascoelho.core.models.Resource
import com.lucascoelho.cstv.utils.getTeamMock
import com.lucascoelho.data.remote.dtos.MatchDto
import com.lucascoelho.data.remote.dtos.toModel
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.models.OpponentModel
import com.lucascoelho.domain.models.PlayerModel
import com.lucascoelho.domain.models.TeamDetailsModel
import com.lucascoelho.domain.usecases.IGetTeamDetailsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MatchDetailsViewModelTest {

    @MockK
    lateinit var getTeamDetailsUseCase: IGetTeamDetailsUseCase

    private lateinit var viewModel: MatchDetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MatchDetailsViewModel(getTeamDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTeamsDetails should emit teams details`() = runTest {
        turbineScope {
            val matchModel = MatchDto().toModel()
            val teams = listOf(
                TeamDetailsModel(EMPTY, EMPTY, listOf(mockk(relaxed = true))),
                TeamDetailsModel(EMPTY, EMPTY, listOf(mockk(relaxed = true))),
            )
            coEvery { getTeamDetailsUseCase(any()) } returns flowOf(Resource.success(teams))

            viewModel.fetchTeamsDetails(matchModel)

            flowOf(viewModel.teams.first()).test {
                assertEquals(Resource.success(teams), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `getTeamPlayersPair should return pair of players for given teams`() {
        val pairPlayers = getPairListOfPlayers()
        val matchModel = MatchModel(
            1,
            mockk(),
            mockk(),
            listOf(OpponentModel(getTeamMock()), OpponentModel(getTeamMock())),
            EMPTY,
            com.lucascoelho.domain.models.MatchStatus.RUNNING,
        )

        val result = viewModel.getTeamPlayersPair(matchModel, getListOfTeams(pairPlayers))

        assertEquals(result.first, pairPlayers.first)
        assertEquals(result.second, pairPlayers.second)
    }

    @Test
    fun `getTeamPlayersPair should return pair of players with empty second list if only one team is present`() {
        val player = getPairListOfPlayers().first
        val teamDetails = TeamDetailsModel("Team1", "Logo1", player)
        val matchModel = MatchModel(
            1,
            mockk(),
            mockk(),
            listOf(OpponentModel(getTeamMock()), OpponentModel(getTeamMock())),
            EMPTY,
            com.lucascoelho.domain.models.MatchStatus.RUNNING,
        )

        val result = viewModel.getTeamPlayersPair(matchModel, listOf(teamDetails))

        assertEquals(result.first, player)
        assertEquals(result.second, emptyList<PlayerModel>())
    }

    private fun getPairListOfPlayers(): Pair<List<PlayerModel>, List<PlayerModel>> {
        val playerOne = PlayerModel("Player1", EMPTY, EMPTY, EMPTY)
        val playerTwo = PlayerModel("Player2", EMPTY, EMPTY, EMPTY)
        val playerThree = PlayerModel("Player3", EMPTY, EMPTY, EMPTY)
        val playerFour = PlayerModel("Player4", EMPTY, EMPTY, EMPTY)

        return Pair(
            listOf(playerOne, playerTwo),
            listOf(playerThree, playerFour),
        )
    }

    private fun getListOfTeams(pair: Pair<List<PlayerModel>, List<PlayerModel>>): List<TeamDetailsModel> {
        return listOf(
            TeamDetailsModel("Team1", "Logo1", pair.first),
            TeamDetailsModel("Team2", "Logo2", pair.second),
        )
    }
}
