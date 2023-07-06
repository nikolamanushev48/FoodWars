package com.mentormate.foodwars.data

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.mentormate.foodwars.R

enum class ScaleRating(
    @StringRes val stringResource: Int,
    @IntegerRes val number: Int
) {
    ABSOLUTELY_TERRIBLE(R.string.absolutely_terrible, 1),
    TERRIBLE(R.string.terrible, 2),
    VERY_BAD(R.string.very_bad, 3),
    BAD(R.string.bad, 4),
    NORMAL(R.string.normal, 5),
    TASTY(R.string.tasty, 6),
    GOOD(R.string.good, 7),
    VERY_GOOD(R.string.very_good, 8),
    DELICIOUS(R.string.delicious, 9),
    ABSOLUTELY_DELICIOUS(R.string.absolutely_delicious, 10);
}
