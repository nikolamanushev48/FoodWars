package com.mentormate.foodwars.data.repository.food

import com.mentormate.foodwars.data.room.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodLocalSource @Inject constructor(
    private val foodDao: FoodDao,
    private val foodWithCharDao: FoodWithCharacteristicsDao,
    private val foodWithCharRelatedItemsDao: FoodWithRelatedItemsDao
) : FoodRepository.FoodLocalSource {

    override fun getAllFoodsWithFlow(): Flow<List<Food>> = foodDao.getAllWithFlow()

    override suspend fun insertAllFood(toBeSaved: List<Food>) =
        foodDao.insertAll(toBeSaved)


    override suspend fun updateFood(food: Food) {
        foodDao.update(food)
    }

    override suspend fun updateTaste(fromTaste: UpdateTaste) {
        foodDao.updateTaste(fromTaste)
    }

    override suspend fun clearAllFoods() = foodDao.clear()

    override suspend fun insertCharacteristics(value: List<Characteristic>) =
        foodDao.insertCharacteristics(value)

    override suspend fun insertRelatedItems(value: List<RelatedItemTable>) =
        foodDao.insertRelatedItems(value)

    override suspend fun insertFoodWithCharacteristicCrossRefs(value: List<FoodCharacteristicCrossRef>) =
        foodWithCharDao.insertFoodCharacteristicCrossRefs(value)

    override suspend fun insertFoodWithRelatedItemCrossRefs(value: List<FoodRelatedItemCrossRef>) =
        foodWithCharRelatedItemsDao.insertFoodRelatedItemCrossRefs(value)

    override fun getFoodWithCharacteristics(foodId: Long) =
        foodWithCharDao.getFoodWithCharacteristicsByFoodId(foodId)

    override fun getFoodWithRelatedItem(foodId: Long) =
        foodWithCharRelatedItemsDao.getFoodWithRelatedItemsByFoodId(foodId)
}