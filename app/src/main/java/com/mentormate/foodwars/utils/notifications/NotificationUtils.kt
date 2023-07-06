package com.mentormate.foodwars.utils.notifications

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavDeepLinkBuilder
import com.mentormate.foodwars.R

@SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
fun NotificationManager.sendNotification(
    context: Context,
    id: Int,
    titleRes: Int,
    messageBodyRes: Int,
    iconRes: Int,
    bigPicture: Boolean
) {

    val pendingIntent = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.navigation)
        .setDestination(R.id.main_screen, null)
        .setArguments(null)
        .createPendingIntent()

    val notification = NotificationCompat.Builder(
        context,
        context.getString(id)
    )
        .setContentTitle(context.getString(titleRes))
        .setContentText(context.getString(messageBodyRes))
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setColor(R.color.red)
        .setContentIntent(pendingIntent)

//    if (bigPicture) {
//        notification.setLargeIcon(context.getDrawable(iconRes)?.toBitmap())
//    } else {
        notification.setSmallIcon(iconRes)
   // }

    notify(id, notification.build())
}