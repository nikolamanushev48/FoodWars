package com.mentormate.foodwars.data.network.service

import com.mentormate.foodwars.data.network.body.FoodBody
import com.mentormate.foodwars.data.network.response.*
import retrofit2.http.*

interface FoodService {
    @GET("item/{id}")
    suspend fun getFood(@Path("id") foodId: Long): BaseResponse<FoodResponse>

    @GET("item")
    suspend fun getAllFoodsByInterest(
        @Query("userId") userId: Long,
        @Query("powerLevelFiltering") powerLevelFiltering: Boolean
    ): BaseResponse<FoodsByInterestResponse>

    @POST("item")
    suspend fun addFood(@Body foodResponse: FoodBody): BaseResponse<FoodResponse>

    @PUT("item/{id}")
    suspend fun update(@Path("id") foodId: Long): BaseResponse<FoodResponse>

    @GET("item/all/{userId}")
    suspend fun getAllByUserId(@Path("userId") userId: Long): BaseResponse<AllFoodResponse>

    @DELETE("item/{itemId}")
    suspend fun delete(@Path("id") foodId: Long): BaseResponse<DeletedFoodResponse>
}