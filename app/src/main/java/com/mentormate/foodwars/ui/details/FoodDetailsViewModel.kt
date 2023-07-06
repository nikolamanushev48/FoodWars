package com.mentormate.foodwars.ui.details

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.domain.vo.details.FoodDetailsUIModel
import com.mentormate.foodwars.ui.constants.FOOD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository
) : BaseViewModel(),
    DefaultLifecycleObserver {

    val uiState: StateFlow<FoodDetailsUIModel>
        get() = _uiState
    private val _uiState = MutableStateFlow(FoodDetailsUIModel())

    private val currentFoodId = savedStateHandle.get<Long>(FOOD_ID)

    init {
        viewModelScope.launch {
            getScreenProperties(currentFoodId)
        }
    }

    private suspend fun getScreenProperties(foodId: Long?) {
        foodId?.let {
            foodRepository.run {
                getFoodWithRelatedItem(foodId)
                    .combine(getFoodWithCharacteristics(it)) { foodWithRelatedItems, foodWithCharacteristics ->
                        foodWithRelatedItems.food.run {
                            FoodDetailsUIModel(
                                name,
                                imageUrl,
                                type,
                                rating,
                                foodWithRelatedItems.relatedItems,
                                foodWithCharacteristics.characteristics
                            )
                        }
                    }.collect {
                        _uiState.value = it
                    }
            }
        }
    }
}