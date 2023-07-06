package com.mentormate.foodwars.ui.topten

import com.mentormate.foodwars.data.room.Food

interface TopTenPresenter {
    fun imageViewOnClick(food: Food)
}