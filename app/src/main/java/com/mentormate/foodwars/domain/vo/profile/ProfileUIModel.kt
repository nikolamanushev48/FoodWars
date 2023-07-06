package com.mentormate.foodwars.domain.vo.profile

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.ui.constants.MAX_HOT
import com.mentormate.foodwars.ui.constants.MIN_HOT
import java.util.*

data class ProfileUIModel(
    val userUIModel: UserUIModel = UserUIModel(),
    val sliderRangePair: Pair<Float, Float> = MIN_HOT to MAX_HOT,
    val subNomenclatures: List<String> = emptyList(),
    val interest: ObservableField<Int> = ObservableField(),
    var lastSync: String = ""
) : BaseObservable() {

    @Bindable
    var sliderRange: Pair<Float, Float> = sliderRangePair
        set(value) {
            field = value
            notifyPropertyChanged(BR.sliderRange)
        }
}