package com.mentormate.foodwars.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.Room

@Database(
    entities = [
        Food::class,
        Characteristic::class,
        FoodCharacteristicCrossRef::class,
        User::class,
        FoodRelatedItemCrossRef::class,
        RelatedItemTable::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FoodWarsDatabase : RoomDatabase() {

    abstract val foodDao: FoodDao
    abstract val userDao: UserDao
    abstract val foodWithRelatedItemsDao: FoodWithRelatedItemsDao
    abstract val foodWithCharacteristicsDao: FoodWithCharacteristicsDao

    companion object {

        @Volatile
        private var INSTANCE: FoodWarsDatabase? = null

        fun getInstance(context: Context): FoodWarsDatabase {
            synchronized(this) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FoodWarsDatabase::class.java,
                        "food_wars_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE as FoodWarsDatabase
            }
        }
    }
}
