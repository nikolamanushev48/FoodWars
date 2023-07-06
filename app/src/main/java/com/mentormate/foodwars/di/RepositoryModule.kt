package com.mentormate.foodwars.di

import com.mentormate.foodwars.data.network.service.FoodService
import com.mentormate.foodwars.data.network.service.InterestService
import com.mentormate.foodwars.data.network.service.UserService
import com.mentormate.foodwars.data.network.service.VoteService
import com.mentormate.foodwars.data.repository.food.FoodLocalSource
import com.mentormate.foodwars.data.repository.food.FoodRemoteSource
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserLocalSource
import com.mentormate.foodwars.data.repository.user.UserRemoteSource
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.data.room.FoodDao
import com.mentormate.foodwars.data.room.FoodWithCharacteristicsDao
import com.mentormate.foodwars.data.room.FoodWithRelatedItemsDao
import com.mentormate.foodwars.data.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesUserLocalSource(userDao: UserDao): UserRepository.UserLocalSource =
        UserLocalSource(userDao)

    @Singleton
    @Provides
    fun providesUserRemoteSource(
        userService: UserService,
        voteService: VoteService,
        interestService: InterestService
    ): UserRepository.UserRemoteSource =
        UserRemoteSource(
            userService,
            voteService,
            interestService
        )

    @Singleton
    @Provides
    fun providesFoodLocalSource(
        foodDao: FoodDao,
        foodWithCharDao: FoodWithCharacteristicsDao,
        foodWithCharRelatedItemsDao: FoodWithRelatedItemsDao
    ): FoodRepository.FoodLocalSource = FoodLocalSource(
        foodDao,
        foodWithCharDao,
        foodWithCharRelatedItemsDao
    )

    @Singleton
    @Provides
    fun providesFoodRemoteSource(
        foodService: FoodService
    ): FoodRepository.FoodRemoteSource = FoodRemoteSource(
        foodService
    )

    @Singleton
    @Provides
    fun providesUserRepository(
        userLocalSource: UserLocalSource,
        userRemoteSource: UserRemoteSource
    ): UserRepository =
        UserRepository(userLocalSource,userRemoteSource)

    @Singleton
    @Provides
    fun providesFoodRepository(
        foodLocalSource: FoodLocalSource,
        foodRemoteSource: FoodRemoteSource
    ): FoodRepository =
        FoodRepository(foodLocalSource,foodRemoteSource)
}
