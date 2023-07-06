package com.mentormate.foodwars.data

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.mentormate.foodwars.R

enum class InterestText(
    @StringRes val stringResource: Int,
    @IntegerRes val number: Int,
    val text: String
) {
    BULGARIAN(R.string.bulgarian, 54,"Bulgarian"),
    TURKISH(R.string.turkish, 55,"Turkish"),
    GREEK(R.string.greek, 56,"Greek"),
    SERBIAN(R.string.serbian, 57,"Serbian"),
    ALBANIAN(R.string.albanian, 58,"Albanian"),
    ROMANIAN(R.string.romanian, 59,"Romanian");
}
