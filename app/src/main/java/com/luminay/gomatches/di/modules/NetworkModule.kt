package com.luminay.gomatches.di.modules

import android.content.Context
import com.example.data.remote.interceptors.CacheInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.luminay.gomatches.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun providesBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun providesConnectionTimeout() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun providesOkHttpClient(
        connectionTimeout: Long,
        @ApplicationContext applicationContext: Context,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val httpCacheDirectory = File(applicationContext.cacheDir, CACHE_DIR)
        val cache = Cache(httpCacheDirectory, CACHE_SIZE)

        return OkHttpClient
            .Builder()
            .readTimeout(connectionTimeout, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(CacheInterceptor())
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun providesNetworkResponseAdapterFactory(): NetworkResponseAdapterFactory = NetworkResponseAdapterFactory()

    @Singleton
    @Provides
    fun providesRetrofit(
        baseUrl: String,
        gson: Gson,
        client: OkHttpClient,
        networkResponseAdapterFactory: NetworkResponseAdapterFactory
    ): Retrofit =
        getRetrofit(
            baseUrl,
            gson,
            client,
            networkResponseAdapterFactory,
        )

    private fun getRetrofit(
        baseUrl: String,
        gson: Gson,
        client: OkHttpClient,
        networkResponseAdapterFactory: NetworkResponseAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(networkResponseAdapterFactory)
        .build()

    companion object {
        private const val BASE_URL = "https://api.pandascore.co"
        private const val NETWORK_TIMEOUT = 60L
        private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
        private const val CACHE_DIR = "http-cache"
    }
}
