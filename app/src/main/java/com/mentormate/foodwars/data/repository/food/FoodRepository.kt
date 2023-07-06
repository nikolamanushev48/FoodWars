package com.mentormate.foodwars.data.repository.food

import com.mentormate.foodwars.data.Taste
import com.mentormate.foodwars.data.network.response.AllFoodResponse
import com.mentormate.foodwars.data.network.response.BaseResponse
import com.mentormate.foodwars.data.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private var localSource: FoodLocalSource,
    private var remoteSource: FoodRemoteSource
) {

    interface FoodLocalSource {
        fun getAllFoodsWithFlow(): Flow<List<Food>>
        suspend fun insertAllFood(toBeSaved: List<Food>): List<Long>
        suspend fun updateFood(food: Food)
        suspend fun updateTaste(fromTaste: UpdateTaste)
        suspend fun clearAllFoods()
        suspend fun insertCharacteristics(value: List<Characteristic>)
        suspend fun insertRelatedItems(value: List<RelatedItemTable>)
        suspend fun insertFoodWithCharacteristicCrossRefs(value: List<FoodCharacteristicCrossRef>)
        suspend fun insertFoodWithRelatedItemCrossRefs(value: List<FoodRelatedItemCrossRef>)
        fun getFoodWithCharacteristics(foodId: Long): Flow<FoodWithCharacteristics>
        fun getFoodWithRelatedItem(foodId: Long): Flow<FoodWithRelatedItems>
    }

    interface FoodRemoteSource {
        suspend fun getAllFoodsByUserIdRemote(userId: Long): BaseResponse<AllFoodResponse>
    }

    fun getAllFoodsWithFlow(): Flow<List<Food>> = localSource.getAllFoodsWithFlow()

    suspend fun updateFood(food: Food) = localSource.updateFood(food)

    suspend fun updateTaste(fromTaste: UpdateTaste) = localSource.updateTaste(fromTaste)

    suspend fun insertAllFood(toBeSaved: List<Food>) =
        localSource.insertAllFood(toBeSaved)

    suspend fun getAllFoodsByUserIdRemote(userId: Long) = remoteSource.getAllFoodsByUserIdRemote(userId)

    suspend fun clearAllFoods() = localSource.clearAllFoods()

    suspend fun insertCharacteristics(value: List<Characteristic>) =
        localSource.insertCharacteristics(value)

    suspend fun insertRelatedItems(value: List<RelatedItemTable>) =
        localSource.insertRelatedItems(value)

    suspend fun insertFoodWithCharacteristicCrossRefs(value: List<FoodCharacteristicCrossRef>) =
        localSource.insertFoodWithCharacteristicCrossRefs(value)

    suspend fun insertFoodWithRelatedItemCrossRefs(value: List<FoodRelatedItemCrossRef>) =
        localSource.insertFoodWithRelatedItemCrossRefs(value)

    fun getFoodWithCharacteristics(foodId: Long) = localSource.getFoodWithCharacteristics(foodId)

    fun getFoodWithRelatedItem(foodId: Long) = localSource.getFoodWithRelatedItem(foodId)

    suspend fun resetVotes() {
        getAllFoodsWithFlow().first().forEach {
            it.taste = Taste.NOT_VOTED
            updateFood(it)
        }
    }
}