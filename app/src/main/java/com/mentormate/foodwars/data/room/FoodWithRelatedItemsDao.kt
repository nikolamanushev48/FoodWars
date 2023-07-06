package com.mentormate.foodwars.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodWithRelatedItemsDao {
    @Insert
    suspend fun insertFoodRelatedItemCrossRefs(crossRefs: List<FoodRelatedItemCrossRef>)

    @Query("SELECT * from food_table WHERE food_Id = :foodId")
    fun getFoodWithRelatedItemsByFoodId(foodId: Long): Flow<FoodWithRelatedItems>
}