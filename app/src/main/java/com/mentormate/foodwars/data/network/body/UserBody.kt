package com.mentormate.foodwars.data.network.body

import com.mentormate.foodwars.data.network.model.UserDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginBody(
    val email: String,
    val password: String,
    val applicationInterestCode: String
)

@Serializable
data class UserRegisteredBody(
    @SerialName("loginAutomatically")
    val autoLogin: Boolean,
    val userDetails: UserDetails
)

@Serializable
data class UserUpdateBody(
    val password: String,
    val interest: Int,
    val pictureUrl: String,
    @SerialName("powerLevelRangeStart")
    val rangeFrom: Float,
    @SerialName("powerLevelRangeEnd")
    val rangeTo: Float
)

@Serializable
data class UserLogoutBody(
    val userId: Long
)