package com.lucascoelho.domain.usecases

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.repositories.IMatchesRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMatchesUseCaseImplTest {

    @MockK
    private lateinit var repository: IMatchesRepository
    private lateinit var useCase: IGetMatchesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetMatchesUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns data from matchesRepository`() = runTest {
        val expectedData = flowOf(Resource.success(listOf(mockk<MatchModel>())))
        every { repository.getAllMatches(1) } returns expectedData

        val result = useCase.invoke(1)

        assertEquals(expectedData, result)
    }
}
