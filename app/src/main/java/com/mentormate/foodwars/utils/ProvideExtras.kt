package com.mentormate.foodwars.utils

import androidx.navigation.fragment.FragmentNavigator

interface ProvideExtras {
    fun getExtra(): FragmentNavigator.Extras
}