package com.mentormate.foodwars.data.network.response

import com.mentormate.foodwars.data.network.model.AllFoodPair
import com.mentormate.foodwars.data.network.model.FoodItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodResponse(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val rating: Int,
    val type: String,
    val characteristics: List<String>
)

@Serializable
data class FoodsByInterestResponse(
    @SerialName("allItems")
    val foodsList: List<FoodItem>
)

@Serializable
data class AllFoodResponse(
    val voteItemsInUserContext: List<AllFoodPair>,
)

@Serializable
data class RelatedItem(
    val id: Long,
    val name: String,
    val imageUrl: String
)

@Serializable
data class DeletedFoodResponse(
    val foodId: Long
)