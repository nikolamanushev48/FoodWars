package com.mentormate.foodwars.data.network.body

import kotlinx.serialization.Serializable

@Serializable
data class InterestBody(
    val categoryCode: String,
    val categoryName: String,
    val isRoot: Boolean
)

@Serializable
data class GetInterestBody(
    val categoryCode: String,
)