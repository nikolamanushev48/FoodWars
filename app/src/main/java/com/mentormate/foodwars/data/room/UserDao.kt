package com.mentormate.foodwars.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * from user_table WHERE user_Id = :key")
    suspend fun get(key: Long): User?

    @Query("DELETE FROM user_table")
    suspend fun clear()

    @Query("SELECT * FROM user_table ORDER BY user_Id DESC LIMIT 1")
    suspend fun getCurrentUser(): User?

    @Query("SELECT * FROM user_table ORDER BY user_Id DESC LIMIT 1")
    fun getCurrentUserWithFlow(): Flow<User>

    @Query("SELECT * FROM user_table ORDER BY user_Id DESC")
    suspend fun getAll(): List<User>
}