package com.mentormate.foodwars

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mentormate.foodwars.data.NotVotedFoodWorker
import com.mentormate.foodwars.data.SyncVotesWorker
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.toBundle
import com.mentormate.foodwars.databinding.ActivityHomeBinding
import com.mentormate.foodwars.utils.ShowMotivationScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { viewModel.splashDataAttr.value == true }
        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        viewModel.navigation.observe(this) {
            if (it is ToDirection) {
                navController.navigate(it.navigationId, it.arguments?.toBundle())
            }
        }

        ClickerTimer(lifecycle) {
            if (it && this.getCurrentFragment() is ShowMotivationScreen) {
                findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_motivationScreen)
            }
        }
        setupRecurringWork()

        createChannel(
            getString(R.string.channel1),
            getString(R.string.new_food_channel_title),
            getString(R.string.new_food_channel_descr),
            NotificationManager.IMPORTANCE_HIGH
        )

        createChannel(
            getString(R.string.channel2),
            getString(R.string.vote_channel_title),
            getString(R.string.vote_channel_descr),
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        val repeatingRequestSyncWorker =
            PeriodicWorkRequestBuilder<SyncVotesWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        val repeatingRequestNotVotedFoodWorker =
            PeriodicWorkRequestBuilder<NotVotedFoodWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SyncVotesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequestSyncWorker
        )

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            NotVotedFoodWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequestNotVotedFoodWorker
        )
    }

    private fun createChannel(
        id: String,
        title: String,
        descriptionText: String,
        importance: Int
    ) {
        NotificationChannel(
            id,
            title,
            importance
        ).apply {
            setShowBadge(false)
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = descriptionText
        }.let { notificationChannel ->
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(notificationChannel)
        }
    }
}