package com.lucascoelho.data.remote.interceptors

import com.lucascoelho.data.BuildConfig
import com.lucascoelho.data.URL
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Test

class RequestInterceptorTest {

    @Test
    fun `RequestInterceptor modifies request with headers and locale query`() {
        val chain: Interceptor.Chain = mockk(relaxed = true)
        val request: Request = Request.Builder().url(URL).build()
        val response: okhttp3.Response = mockk(relaxed = true)

        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        val requestInterceptor = RequestInterceptor()
        requestInterceptor.intercept(chain)

        verify {
            chain.proceed(withArg { modifiedRequest ->
                TestCase.assertEquals(
                    BuildConfig.API_KEY,
                    modifiedRequest.url.queryParameter(RequestInterceptor.TOKEN)
                )
            })
        }
    }
}
