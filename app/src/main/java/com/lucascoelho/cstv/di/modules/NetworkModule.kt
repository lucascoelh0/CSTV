package com.lucascoelho.cstv.di.modules

import android.content.Context
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.lucascoelho.cstv.BuildConfig
import com.lucascoelho.data.remote.api.MatchesApi
import com.lucascoelho.data.remote.api.TeamsApi
import com.lucascoelho.data.remote.interceptors.CacheInterceptor
import com.lucascoelho.data.remote.interceptors.RequestInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

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
            .addInterceptor(RequestInterceptor())
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
        moshi: Moshi,
        client: OkHttpClient,
        networkResponseAdapterFactory: NetworkResponseAdapterFactory,
    ): Retrofit =
        getRetrofit(
            baseUrl,
            moshi,
            client,
            networkResponseAdapterFactory,
        )

    private fun getRetrofit(
        baseUrl: String,
        moshi: Moshi,
        client: OkHttpClient,
        networkResponseAdapterFactory: NetworkResponseAdapterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(networkResponseAdapterFactory)
        .build()

    @Singleton
    @Provides
    fun providesMatchesApi(retrofit: Retrofit): MatchesApi = retrofit.create(MatchesApi::class.java)

    @Singleton
    @Provides
    fun provideTeamsApi(retrofit: Retrofit): TeamsApi = retrofit.create(TeamsApi::class.java)

    companion object {
        private const val BASE_URL = "https://api.pandascore.co/csgo/"
        private const val NETWORK_TIMEOUT = 60L
        private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
        private const val CACHE_DIR = "http-cache"
    }
}
