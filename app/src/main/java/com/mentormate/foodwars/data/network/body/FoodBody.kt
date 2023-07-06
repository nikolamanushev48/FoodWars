package com.mentormate.foodwars.data.network.body

import kotlinx.serialization.Serializable

@Serializable
data class FoodBody(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val rating: Int,
    val type: String
)