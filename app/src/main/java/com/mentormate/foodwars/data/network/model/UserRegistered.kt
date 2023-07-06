package com.mentormate.foodwars.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistered(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val interest: Int,
    val gender: String,
    val powerLevelRangeStart: Long,
    val powerLevelRangeEnd: Long,
    val pictureUrl: String
)