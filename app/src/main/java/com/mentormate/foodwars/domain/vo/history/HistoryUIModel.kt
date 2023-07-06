package com.mentormate.foodwars.domain.vo.history

import androidx.databinding.BaseObservable

data class HistoryUIModel(
    val entityList: List<HistoryListItemUIModel> = emptyList()
) : BaseObservable()