package com.mentormate.foodwars.domain.vo.topten

import com.mentormate.foodwars.data.room.Food
import com.mentormate.foodwars.ui.constants.INVALID_RES

sealed class CardItem

data class TopTenEntityUIModel(
    val resource: String = "",
    val nameRes: String = "",
    val type: Int = INVALID_RES,
    val food: Food = Food(),
    val cardColor: Int = INVALID_RES
) : CardItem()

data class HeaderUiModel(
    val textResource: Int = INVALID_RES
) : CardItem()