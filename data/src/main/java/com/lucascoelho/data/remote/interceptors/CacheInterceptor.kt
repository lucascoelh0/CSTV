package com.lucascoelho.data.remote.interceptors

import okhttp3.CacheControl
import okhttp3.CacheControl.Builder
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val cacheControl: CacheControl = Builder()
            .maxAge(MAX_AGE, TimeUnit.SECONDS)
            .build()
        return response.newBuilder()
            .removeHeader(PRAGMA_HEADER)
            .removeHeader(CACHE_CONTROL_HEADER)
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }

    companion object {
        const val PRAGMA_HEADER = "Pragma"
        const val CACHE_CONTROL_HEADER = "Cache-Control"
        const val MAX_AGE = 30
    }
}
