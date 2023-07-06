package com.mentormate.foodwars.data

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.network.body.VotesForSync
import com.mentormate.foodwars.data.network.body.VotesSyncBody
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.utils.notifications.sendNotification
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class SyncVotesWorker @Inject constructor(
    appContext: Context,
    params: WorkerParameters,
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : CoroutineWorker(
    appContext,
    params
) {

    companion object {
        const val WORK_NAME = "com.mentormate.foodwars.data.SyncVotesWorker"
    }

    private val notificationManager = ContextCompat.getSystemService(
        appContext,
        NotificationManager::class.java
    ) as NotificationManager

    override suspend fun doWork(): Result {

        try {
            with(userRepository) {
                foodRepository.getAllFoodsWithFlow().first().map {
                    VotesForSync(
                        userRepository.readCurrentUser().userId,
                        it.foodId,
                        it.taste.name
                    )
                }.let { votesForSyncList ->
                    syncVotes(
                        VotesSyncBody(
                            votesForSyncList,
                            System.currentTimeMillis().toString()
                        )
                    )
                }

                readCurrentUser().let {
                    it.lastSyncTime = Calendar.getInstance().time.toString()
                    update(it)
                }
            }
            notificationManager.sendNotification(
                applicationContext,
                R.string.channel1,
                R.string.new_food_channel_title,
                R.string.new_food_channel_descr,
                R.drawable.app_logo,
                true
            )

        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}