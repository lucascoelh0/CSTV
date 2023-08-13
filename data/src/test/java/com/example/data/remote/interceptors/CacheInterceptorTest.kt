package com.example.data.remote.interceptors

import com.example.data.MAX_AGE
import com.example.data.OK
import com.example.data.STATUS_OK
import com.example.data.URL
import com.example.data.remote.interceptors.CacheInterceptor.Companion.CACHE_CONTROL_HEADER
import com.example.data.remote.interceptors.CacheInterceptor.Companion.PRAGMA_HEADER
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Test

class CacheInterceptorTest {

    @Test
    fun `CacheInterceptor adds cache control headers`() {
        val chain: Interceptor.Chain = mockk(relaxed = true)
        val request: Request = Request.Builder().url(URL).build()
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(STATUS_OK)
            .message(OK)
            .build()

        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        val cacheInterceptor = CacheInterceptor()
        val modifiedResponse = cacheInterceptor.intercept(chain)

        assertNull(modifiedResponse.header(PRAGMA_HEADER))
        assertEquals(modifiedResponse.header(CACHE_CONTROL_HEADER), MAX_AGE)
    }
}
