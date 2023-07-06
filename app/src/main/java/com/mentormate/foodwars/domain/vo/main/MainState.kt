package com.mentormate.foodwars.domain.vo.main

import androidx.databinding.BaseObservable

data class MainState(
    val foodUIModelList: List<FoodUIModel> = emptyList()
) : BaseObservable()