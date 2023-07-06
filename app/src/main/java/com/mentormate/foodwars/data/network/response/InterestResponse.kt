package com.mentormate.foodwars.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class InterestResponse(
    val id: Long,
    val categoryCode: String,
    val categoryName: String,
    val isRoot: Boolean
)

@Serializable
data class AllInterestsResponse(
    val areSubnomenclatures: Boolean,
    val categories: List<InterestResponse>,
)

fun List<InterestResponse>.toListOfString() = map { it.categoryName }
