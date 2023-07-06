package com.mentormate.foodwars.domain.vo.history

import androidx.databinding.BaseObservable
import com.mentormate.foodwars.data.room.Food
import com.mentormate.foodwars.ui.constants.INVALID_RES

data class HistoryListItemUIModel(
    val resource: String = "",
    val nameRes: String = "",
    val type: Int = INVALID_RES,
    val food: Food = Food()
) : BaseObservable()