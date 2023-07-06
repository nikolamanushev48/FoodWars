package com.mentormate.foodwars.data.repository.food

import com.mentormate.foodwars.data.network.response.AllFoodResponse
import com.mentormate.foodwars.data.network.response.BaseResponse
import com.mentormate.foodwars.data.network.response.FoodsByInterestResponse
import com.mentormate.foodwars.data.network.service.FoodService
import javax.inject.Inject

class FoodRemoteSource @Inject constructor(
    private val foodService: FoodService
) : FoodRepository.FoodRemoteSource {

    override suspend fun getAllFoodsByUserIdRemote(userId: Long): BaseResponse<AllFoodResponse> =
        foodService.getAllByUserId(userId)
}