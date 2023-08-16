package com.example.domain.usecases

import com.example.core.constants.EMPTY
import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import com.example.domain.models.TeamDetailsModel
import com.example.domain.repositories.IMatchesRepository
import com.example.domain.repositories.ITeamsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTeamDetailsUseCaseImplTest {

    @MockK
    private lateinit var repository: ITeamsRepository
    private lateinit var useCase: IGetTeamDetailsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetTeamDetailsUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns data from teamsRepository`() = runTest {
        val expectedData = flowOf(Resource.success(listOf(mockk<TeamDetailsModel>())))
        every { repository.getTeamDetails(EMPTY) } returns expectedData

        val result = useCase.invoke(emptyList())

        assertEquals(expectedData, result)
    }
}
