package com.mentormate.foodwars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DELAY = 1000L

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    val splashDataAttr: LiveData<Boolean>
        get() = _splashDataAttr

    private val _splashDataAttr = MutableLiveData(true)

    init {
        viewModelScope.launch {
            navigate()
        }
    }

    private suspend fun navigate() {
        userRepository.run {
            _navigation.value = if (isUserAvailable()) {
                    if (loginStatus(readCurrentUser().userId).response.status == "ALREADY_LOGGED_IN" ||
                        loginStatus(readCurrentUser().userId).response.status == "LOGGED_IN"
                    ) {
                        foodRepository.resetVotes()
                        ToDirection(R.id.main_screen)
                    } else {
                        ToDirection(R.id.action_global_loginScreen)
                    }
                } else {
                    ToDirection(R.id.action_global_loginScreen)
                }
        }
        delay(DELAY)
        _splashDataAttr.value = false
    }
}