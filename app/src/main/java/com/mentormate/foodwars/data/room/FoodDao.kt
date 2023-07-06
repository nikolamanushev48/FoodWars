package com.mentormate.foodwars.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert
    suspend fun insertCharacteristics(characteristics: List<Characteristic>)

    @Insert
    suspend fun insertRelatedItems(relatedItems: List<RelatedItemTable>)

    @Insert
    suspend fun insertAll(foodList: List<Food>): List<Long>

    @Update
    suspend fun update(newFood: Food)

    @Query("SELECT * from food_table WHERE food_Id = :foodId")
    suspend fun getById(foodId: Long): Food?

    @Query("DELETE FROM food_table")
    suspend fun clear()

    @Update(entity = Food::class)
    suspend fun updateTaste(fromTaste: UpdateTaste)

    @Query("SELECT * FROM food_table ORDER BY food_Id DESC LIMIT 1")
    suspend fun getCurrentFood(): Food?

    @Transaction
    @Query("SELECT * FROM food_table")
    fun getAllWithFlow(): Flow<List<Food>>
}