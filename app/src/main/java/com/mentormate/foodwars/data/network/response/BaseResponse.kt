package com.mentormate.foodwars.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val httpCode: String,
    val message: String,
    val response: T,
)