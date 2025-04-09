package com.mentormate.foodwars.data.network.service

import com.mentormate.foodwars.data.network.body.UserLoginBody
import com.mentormate.foodwars.data.network.body.UserLogoutBody
import com.mentormate.foodwars.data.network.body.UserRegisteredBody
import com.mentormate.foodwars.data.network.body.UserUpdateBody
import com.mentormate.foodwars.data.network.model.UserRegistered
import com.mentormate.foodwars.data.network.response.*
import retrofit2.http.*

interface UserService {
    @GET("user/{id}")
    suspend fun getUser(@Path("id") userId: Long): BaseResponse<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(@Body userRegistered: UserRegisteredBody): BaseResponse<UserRegistered>

    @PUT("user/{id}")
    suspend fun update(
        @Path("id") userId: Long,
        @Body userUpdateBody: UserUpdateBody
    ): BaseResponse<UserUpdateResponse>

    @POST("auth/login")
    suspend fun login(@Body userLoginRequest: UserLoginBody): BaseResponse<UserLogin>

    @GET("auth/login-status/{userId}")
    suspend fun loginStatus(@Path("userId") userId: Long): BaseResponse<UserLoginStatus>

    @HTTP(method = "DELETE", path = "logout", hasBody = true)
    suspend fun logout(@Body userLogoutBody: UserLogoutBody)
}