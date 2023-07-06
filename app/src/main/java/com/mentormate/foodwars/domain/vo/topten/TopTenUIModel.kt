package com.mentormate.foodwars.domain.vo.topten

import androidx.databinding.BaseObservable

data class TopTenUIModel(
    val entityList: List<CardItem> = emptyList()
) : BaseObservable()