package com.mentormate.foodwars.di


import android.app.Application
import androidx.room.Room
import com.mentormate.foodwars.data.room.FoodWarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(application: Application): FoodWarsDatabase = Room.databaseBuilder(
        application.applicationContext,
        FoodWarsDatabase::class.java,
        "food_wars_database"
    ).build()

    @Singleton
    @Provides
    fun providesUserDatabaseDao(database: FoodWarsDatabase) = database.userDao

    @Singleton
    @Provides
    fun providesFoodDatabaseDao(database: FoodWarsDatabase) = database.foodDao

    @Singleton
    @Provides
    fun providesFoodWithCharacteristicsDao(database: FoodWarsDatabase) = database.foodWithCharacteristicsDao

    @Singleton
    @Provides
    fun providesFoodWithRelatedItemsDao(database: FoodWarsDatabase) = database.foodWithRelatedItemsDao
}
