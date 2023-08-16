package com.example.data.remote.repositories

import com.example.core.models.Status
import com.example.data.NETWORK_ERROR
import com.example.data.SERVER_ERROR
import com.example.data.STATUS_ERROR
import com.example.data.STATUS_OK
import com.example.data.UNKNOWN_ERROR
import com.example.data.remote.api.MatchesApi
import com.example.data.remote.dtos.MatchDto
import com.example.data.remote.dtos.common.GenericErrorResponse
import com.example.data.remote.dtos.toModel
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

class MatchesRepositoryImplTest {

    @MockK
    private lateinit var matchesApi: MatchesApi
    private lateinit var matchesRepository: MatchesRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        matchesRepository = MatchesRepositoryImpl(matchesApi)
    }

    @Test
    fun `getTeamDetails returns successful Resource`() = runTest {
        val expected = listOf(MatchDto())
        coEvery { matchesApi.getAllMatches() } returns NetworkResponse.Success(expected, code = STATUS_OK)

        val result = matchesRepository.getAllMatches(1).first()

        assertEquals(Status.SUCCESS, result.status)
        assertEquals(expected.toModel(), result.data)
    }

    @Test
    fun `getTeamDetails returns server error`() = runTest {
        val serverErrorResponse = GenericErrorResponse(SERVER_ERROR, STATUS_ERROR)
        coEvery {
            matchesApi.getAllMatches(1)
        } returns NetworkResponse.ServerError(serverErrorResponse, STATUS_ERROR)

        val result = matchesRepository.getAllMatches(1).first()

        assertEquals(Status.ERROR, result.status)
        assertEquals(STATUS_ERROR, result.errorStatus)
        assertEquals(SERVER_ERROR, result.message)
    }

    @Test
    fun `getTeamDetails returns network error`() = runTest {
        coEvery { matchesApi.getAllMatches(1) } returns NetworkResponse.NetworkError(IOException(NETWORK_ERROR))

        matchesRepository.getAllMatches(1).collect { result ->
            assertEquals(Status.ERROR, result.status)
        }
    }

    @Test
    fun `getTeamDetails returns unknown error`() = runTest {
        coEvery { matchesApi.getAllMatches(1) } returns NetworkResponse.UnknownError(Throwable(UNKNOWN_ERROR))

        matchesRepository.getAllMatches(1).collect { result ->
            assertEquals(Status.ERROR, result.status)
        }
    }
}
