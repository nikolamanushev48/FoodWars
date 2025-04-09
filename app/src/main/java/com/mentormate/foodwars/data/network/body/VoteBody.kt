package com.mentormate.foodwars.data.network.body

import kotlinx.serialization.Serializable


@Serializable
data class VotesSyncBody(
    val votesForSynchronization: List<VotesForSync?>,
    val synchronizationMoment: String
)

@Serializable
data class VotesForSync(
    val userId: Long?,
    val itemId: Long,
    val votedStatus: String
)
