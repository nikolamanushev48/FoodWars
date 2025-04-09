package com.mentormate.foodwars.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mentormate.foodwars.BuildConfig
import com.mentormate.foodwars.data.network.service.FoodService
import com.mentormate.foodwars.data.network.service.InterestService
import com.mentormate.foodwars.data.network.service.UserService
import com.mentormate.foodwars.data.network.service.VoteService
import com.mentormate.foodwars.ui.constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideMediaType(): MediaType =
        "application/json".toMediaType()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit)
            : UserService = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideFoodService(retrofit: Retrofit)
            : FoodService = retrofit.create(FoodService::class.java)

    @Singleton
    @Provides
    fun provideVoteService(retrofit: Retrofit)
            : VoteService = retrofit.create(VoteService::class.java)

    @Singleton
    @Provides
    fun provideInterestService(retrofit: Retrofit)
            : InterestService = retrofit.create(InterestService::class.java)

    @Singleton
    @Provides
    fun provideRetrofitClient() : OkHttpClient = OkHttpClient.Builder()
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(Interceptor { chain ->
            with(chain.request().newBuilder(), fun Request.Builder.(): Request {
                header("Content-Type", "application/json") // Add this
                return build()
            }).let { requestWithHeaders ->
                chain.proceed(requestWithHeaders)
            }
        })
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        })
        .build()

}
