package com.mentormate.foodwars.data.network.body

import com.mentormate.foodwars.data.network.model.UserDetails
import com.mentormate.foodwars.data.room.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginBody(
    val email: String,
    val password: String
)

@Serializable
data class UserRegisteredBody(
    @SerialName("loginAutomatically")
    val autoLogin: Boolean,
    val user: User
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