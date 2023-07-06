package com.mentormate.foodwars.data.network.model

import com.mentormate.foodwars.data.room.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val interest: Int,
    val gender: String,
    val pictureUrl: String
)

fun User.toUserDetails() = UserDetails(
    firstName = firstName,
    lastName = lastName,
    email = email,
    interest = interest,
    gender = gender,
    pictureUrl = localPicture,
    password = password
)