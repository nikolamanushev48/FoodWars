package com.mentormate.foodwars.data

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mentormate.foodwars.R
import com.mentormate.foodwars.utils.notifications.sendNotification
import retrofit2.HttpException
import javax.inject.Inject

class NotVotedFoodWorker @Inject constructor(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(
    appContext,
    params
) {

    companion object {
        const val WORK_NAME = "com.mentormate.foodwars.data.NotVotedFoodWorker"
    }

    private val notificationManager = ContextCompat.getSystemService(
        appContext,
        NotificationManager::class.java
    ) as NotificationManager

    override suspend fun doWork(): Result {

        try {
            notificationManager.sendNotification(
                applicationContext,
                R.string.channel2,
                R.string.vote_channel_title,
                R.string.vote_channel_descr,
                R.drawable.new_food_icon,
                false
            )
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}