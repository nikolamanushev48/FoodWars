package com.mentormate.foodwars.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    val userId: Long,
    @SerialName("itemId")
    val foodId: Long
)

@Serializable
data class VoteSyncResponse(
    val userId: Long,
    @SerialName("isSynchronizedSuccessfully")
    val isSynced: Boolean
)

@Serializable
data class VotesClearedResponse(
    val userId: Long,
    val clearedVoteStatus: String,
    val timeOfExecution: String
)