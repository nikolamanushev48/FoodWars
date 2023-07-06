package com.mentormate.foodwars.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.mentormate.foodwars.data.LocationLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideLocationManager(application: Application) =
        application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Singleton
    @Provides
    fun provideLocationLiveData(application: Application) = LocationLiveData(application)

}
