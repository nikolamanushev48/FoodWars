package com.mentormate.foodwars.data

sealed class Destination

open class External : Destination()
open class Internal : Destination()

data class ToDirection(
    val navigationId: Int,
    val arguments: Map<String, Any>? = null
) : Internal()

object PopBack : Internal()

object ToGallery : External()
object CheckPermissions : External()
object CheckNotificationPermissions : External()
open class ToSettings : External()
data class ShareViaEmail(
    val firstName: String,
    val lastName: String,
    val foodName: String,
    val type: Int
) : External()