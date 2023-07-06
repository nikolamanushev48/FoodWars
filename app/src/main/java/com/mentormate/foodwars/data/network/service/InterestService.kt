package com.mentormate.foodwars.data.network.service

import com.mentormate.foodwars.data.network.response.AllInterestsResponse
import com.mentormate.foodwars.data.network.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InterestService {
    @GET("interest/{id}")
    suspend fun getInterestById(@Path("id") interestId: Int, @Query("getSubNomenclatures") getSubNomenclatures: Boolean): BaseResponse<AllInterestsResponse>
}