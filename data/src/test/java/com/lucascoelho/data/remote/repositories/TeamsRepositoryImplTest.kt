package com.lucascoelho.data.remote.repositories

import com.lucascoelho.core.constants.EMPTY
import com.lucascoelho.core.models.Status
import com.lucascoelho.data.NETWORK_ERROR
import com.lucascoelho.data.SERVER_ERROR
import com.lucascoelho.data.STATUS_ERROR
import com.lucascoelho.data.STATUS_OK
import com.lucascoelho.data.UNKNOWN_ERROR
import com.lucascoelho.data.remote.api.TeamsApi
import com.lucascoelho.data.remote.dtos.TeamDetailsDto
import com.lucascoelho.data.remote.dtos.common.GenericErrorResponse
import com.lucascoelho.data.remote.dtos.toModel
import com.haroldadmin.cnradapter.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TeamsRepositoryImplTest {

    @MockK
    private lateinit var teamsApi: TeamsApi
    private lateinit var teamsRepository: TeamsRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        teamsRepository = TeamsRepositoryImpl(teamsApi)
    }

    @Test
    fun `getTeamDetails returns successful Resource`() = runTest {
        val expected = listOf(
            TeamDetailsDto(
                EMPTY,
                EMPTY,
                emptyList(),
            )
        )
        coEvery { teamsApi.getTeamDetails(EMPTY) } returns NetworkResponse.Success(expected, code = STATUS_OK)

        val result = teamsRepository.getTeamDetails(EMPTY).first()

        assertEquals(Status.SUCCESS, result.status)
        assertEquals(expected.toModel(), result.data)
    }

    @Test
    fun `getTeamDetails returns server error`() = runTest {
        val serverErrorResponse = GenericErrorResponse(SERVER_ERROR, STATUS_ERROR)
        coEvery {
            teamsApi.getTeamDetails(EMPTY)
        } returns NetworkResponse.ServerError(serverErrorResponse, STATUS_ERROR)

        val result = teamsRepository.getTeamDetails(EMPTY).first()

        assertEquals(Status.ERROR, result.status)
        assertEquals(STATUS_ERROR, result.errorStatus)
        assertEquals(SERVER_ERROR, result.message)
    }

    @Test
    fun `getTeamDetails returns network error`() = runTest {
        coEvery { teamsApi.getTeamDetails(EMPTY) } returns NetworkResponse.NetworkError(IOException(NETWORK_ERROR))

        teamsRepository.getTeamDetails(EMPTY).collect { result ->
            assertEquals(Status.ERROR, result.status)
        }
    }

    @Test
    fun `getTeamDetails returns unknown error`() = runTest {
        coEvery { teamsApi.getTeamDetails(EMPTY) } returns NetworkResponse.UnknownError(Throwable(UNKNOWN_ERROR))

        teamsRepository.getTeamDetails(EMPTY).collect { result ->
            assertEquals(Status.ERROR, result.status)
        }
    }
}
