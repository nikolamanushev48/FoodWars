package com.mentormate.foodwars.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentormate.foodwars.ui.constants.INVALID_ID
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_Id")
    val userId: Long = INVALID_ID,
    @ColumnInfo(name = "first_name")
    val firstName: String = "",
    @ColumnInfo(name = "last_name")
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val interest: Int = 0,
    val spicyLevel: Int = 0,
    var localPicture: String = "",
    val remotePicture: String = "",
    val password: String = "",
    var lastSyncTime: String = "",
    val activeToken: String? = null
)