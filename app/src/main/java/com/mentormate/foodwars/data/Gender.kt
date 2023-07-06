package com.mentormate.foodwars.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mentormate.foodwars.R

enum class Gender(
    @StringRes val stringResource: Int,
    @DrawableRes val imgResource: Int,
    val serverName: String,
) {
    MAN(R.string.man, R.drawable.male_icon,"MALE"),
    WOMAN(R.string.woman, R.drawable.female_icon,"FEMALE"),
    OTHER(R.string.other, R.drawable.transparent_icon,"OTHER");
}