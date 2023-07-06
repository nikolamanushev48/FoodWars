package com.mentormate.foodwars.ui.main

import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.ShareViaEmail
import com.mentormate.foodwars.data.Taste
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.data.room.Food
import com.mentormate.foodwars.data.room.UpdateTaste
import com.mentormate.foodwars.data.room.toFoodUIModel
import com.mentormate.foodwars.domain.vo.main.FoodUIModel
import com.mentormate.foodwars.domain.vo.main.MainState
import com.mentormate.foodwars.ui.constants.FOOD_ID
import com.mentormate.foodwars.utils.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    val uiState: StateFlow<MainState> = initialState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = MainState()
    )

    fun shareFood(currFood: FoodUIModel) {
        viewModelScope.launch {
            userRepository.readCurrentUser().let { user ->
                _navigation.value =
                    ShareViaEmail(
                        user.firstName,
                        user.lastName,
                        currFood.name,
                        currFood.type
                    )
            }
        }
    }

    private fun initialState(): Flow<MainState> =
        userRepository.getCurrentUserWithFlow().let { userFlow ->
            foodRepository.getAllFoodsWithFlow()
                .combine(userFlow) { foods, user ->
                    foods.filter {
                        it.type == user.interest
                    }.let {
                        it.filter { food ->
                            food.taste == Taste.NOT_VOTED
                        }.map { food ->
                            food.toFoodUIModel()
                        }
                    }
                }.map {
                    MainState(it)
                }
        }

    fun cardImageOnClicked(foodId: Long) {
        _navigation.value =
            ToDirection(
                R.id.action_main_screen_to_foodDetailsFragment,
                mapOf(FOOD_ID to foodId)
            )
    }

    fun updateItem(
        foodId: Long,
        direction: Direction
    ) {
        when (direction) {
            Direction.Right -> Taste.TASTY
            Direction.Left -> Taste.GROSS
            else -> Taste.NOT_VOTED
        }.let {
            viewModelScope.launch {
                foodRepository.updateTaste(UpdateTaste(foodId, it))
            }
        }
    }
}