package com.mentormate.foodwars.data.network.response

import com.mentormate.foodwars.data.room.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    var interest: Int,
    val gender: String,
    val pictureUrl: String,
    val powerLevelRangeStart: Float,
    val powerLevelRangeEnd: Float
)

@Serializable
data class UserUpdateResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    var interest: Int,
    val gender: String,
    val pictureUrl: String,
    val powerLevelRangeStart: Float,
    val powerLevelRangeEnd: Float
)

@Serializable
data class UserLogin(
    val userId: Long,
    val status: String
)

@Serializable
data class UserLoginStatus(
    val userId: Long,
    val status: String
)

@Serializable
data class UserLogout(
    val userId: Long,
    val status: String,
    val logoutTime: String
)

fun UserResponse.toUser() = User(
    userId = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    interest = interest,
    gender = gender,
    remotePicture = pictureUrl
)