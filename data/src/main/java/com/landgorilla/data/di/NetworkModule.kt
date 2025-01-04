package com.landgorilla.data.di

import android.content.Context
import androidx.multidex.BuildConfig
import com.google.gson.GsonBuilder
import com.landgorilla.data.mapper.ErrorResponseMapper
import com.landgorilla.data.remote.api.MovieService
import com.landgorilla.data.remote.utils.Constants.API_URL
import com.landgorilla.data.remote.utils.Constants.API_TOKEN
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        cacheInterceptor: Interceptor,
        authorizationInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(cacheInterceptor)
            .addInterceptor(authorizationInterceptor)
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            }
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            // .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext appContext: Context): Cache {
        return Cache(
            File(appContext.applicationContext.cacheDir, "movie_cache"),
            10 * 1024 * 1024 // 10MB cache size
        )
    }

//    @Provides
//    @Singleton
//    fun provideAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
//        val originalRequest = chain.request()
//        val requestWithAuthorization = originalRequest.newBuilder()
//            .header("Authorization", "Bearer ${API_TOKEN}")
//            .build()
//        chain.proceed(requestWithAuthorization)
//    }
//
//    @Provides
//    @Singleton
//    fun provideCacheInterceptor(): Interceptor = Interceptor { chain ->
//        val response: Response = chain.proceed(chain.request())
//        val cacheControl = CacheControl.Builder()
//            .maxAge(30, TimeUnit.DAYS)
//            .build()
//        response.newBuilder()
//            .header("Cache-Control", cacheControl.toString())
//            .build()
//    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestWithAuthorization = originalRequest.newBuilder()
            .header("Authorization", "Bearer $API_TOKEN")
            .build()

        val response: Response = chain.proceed(requestWithAuthorization)
        val cacheControl = CacheControl.Builder()
            .maxAge(30, TimeUnit.DAYS)
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }

    @Provides
    @Singleton
    fun provideErrorResponseMapper(): ErrorResponseMapper {
        return ErrorResponseMapper()
    }
}