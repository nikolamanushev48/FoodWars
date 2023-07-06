package com.mentormate.foodwars.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodWithCharacteristicsDao {
    @Insert
    suspend fun insertFoodCharacteristicCrossRefs(crossRefs: List<FoodCharacteristicCrossRef>)

    @Query("SELECT * from food_table WHERE food_Id = :foodId")
    fun getFoodWithCharacteristicsByFoodId(foodId: Long): Flow<FoodWithCharacteristics>

    @Transaction
    @Query("SELECT * FROM food_table")
    suspend fun getAll(): List<FoodWithCharacteristics>
}