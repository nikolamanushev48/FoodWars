package com.mentormate.foodwars.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.Taste
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.room.Food
import com.mentormate.foodwars.data.room.toHistoryListItemUIModel
import com.mentormate.foodwars.domain.vo.history.HistoryListItemUIModel
import com.mentormate.foodwars.domain.vo.history.HistoryUIModel
import com.mentormate.foodwars.ui.constants.FOOD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), HistoryPresenter {

    val uiHistoryData: LiveData<HistoryUIModel>
        get() = _uiHistoryData

    private var _uiHistoryData = MutableLiveData(HistoryUIModel())

    init {
        viewModelScope.launch {
            foodRepository.getAllFoodsWithFlow().let {
                _uiHistoryData.value =
                    HistoryUIModel(
                        handleEntityList(it)
                    )
            }
        }
    }

    private suspend fun handleEntityList(foodList: Flow<List<Food>>): List<HistoryListItemUIModel> =
        foodList.first().filter {
            it.taste == Taste.NOT_VOTED
        }.map { food ->
            food.toHistoryListItemUIModel()
        }

    override fun cardViewOnClick(food: Food) {
        _navigation.value = ToDirection(
            R.id.action_history_screen_to_food_details_fragment,
            mapOf(FOOD_ID to food.foodId)
        )
    }
}