package com.mentormate.foodwars.data.network.model

import com.mentormate.foodwars.data.ScaleRating
import com.mentormate.foodwars.data.network.response.RelatedItem
import com.mentormate.foodwars.data.room.Food
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class AllFoodPair(
    val item: FoodItem,
    val voteStatus: String
)

@Serializable
data class FoodItem(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val rating: Int,
    val type: Int,
    val level: Int,
    @SerialName("characteristics")
    val characteristicList: List<String>,
    val relatedItems: List<RelatedItem>,

)

fun FoodItem.toFood() = Food(
    foodId = id,
    name = name,
    imageUrl = imageUrl,
    type = type,
    rating = ScaleRating.values()[rating],
    spiceLevel = level
)