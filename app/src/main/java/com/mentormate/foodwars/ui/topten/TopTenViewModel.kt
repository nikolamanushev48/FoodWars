package com.mentormate.foodwars.ui.topten

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.room.Food
import com.mentormate.foodwars.domain.vo.topten.CardItem
import com.mentormate.foodwars.domain.vo.topten.HeaderUiModel
import com.mentormate.foodwars.domain.vo.topten.TopTenEntityUIModel
import com.mentormate.foodwars.domain.vo.topten.TopTenUIModel
import com.mentormate.foodwars.ui.constants.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopTenViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), TopTenPresenter {
    val uiTopTenData: LiveData<TopTenUIModel>
        get() = _uiTopTenData

    private var _uiTopTenData = MutableLiveData(TopTenUIModel())

    init {
        viewModelScope.launch {
            foodRepository.getAllFoodsWithFlow().first().let {
                if (it.size >= TOPTEN_ELEMENTS_NEEDED) {
                    _uiTopTenData.value =
                        TopTenUIModel(
                            handleEntityList(it)
                        )
                }
            }
        }
    }

    private fun handleEntityList(foodList: List<Food>): List<CardItem> =
        makeSection(
            R.string.hall_of_fame,
            foodList.shuffled().take(TOPTEN_ELEMENTS_NEEDED)
        ) + makeSection(
            R.string.with_great_flavour_comes_great_eating,
            foodList.subList(TOPTEN_ELEMENTS_NEEDED, foodList.size).take(3)
        )

    private fun makeSection(
        header: Int,
        foodList: List<Food>
    ) =
        listOf(HeaderUiModel(header)) + makeEntity(foodList)

    private fun makeEntity(tempList: List<Food>): List<CardItem> =
        tempList.mapIndexed { index, food ->
            food.run {
                TopTenEntityUIModel(
                    imageUrl,
                    name,
                    type,
                    this,
                    setCardViewColor(100 - index * 10)
                )
            }
        }

    private fun setCardViewColor(percent: Int) =
        Color.argb(percent, CUSTOM_RED, CUSTOM_GREEN, CUSTOM_BLUE)

    override fun imageViewOnClick(food: Food) {
        _navigation.value = ToDirection(
            R.id.action_top_ten_screen_to_food_details_fragment,
            mapOf(FOOD_ID to food.foodId)
        )
    }
}