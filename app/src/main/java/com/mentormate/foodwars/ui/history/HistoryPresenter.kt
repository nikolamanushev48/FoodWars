package com.mentormate.foodwars.ui.history

import com.mentormate.foodwars.data.room.Food

interface HistoryPresenter {
    fun cardViewOnClick(food: Food)
}