package com.mentormate.foodwars.data.network.service

import com.mentormate.foodwars.data.network.body.VotesSyncBody
import com.mentormate.foodwars.data.network.response.BaseResponse
import com.mentormate.foodwars.data.network.response.VoteSyncResponse
import com.mentormate.foodwars.data.network.response.VotesClearedResponse
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface VoteService {
    @HTTP(method = "DELETE", path = "vote", hasBody = true)
    suspend fun clearVotes(@Path("userId") userId: Long): BaseResponse<VotesClearedResponse>

    @POST("vote/synchronize")
    suspend fun syncVotes(@Body votesSyncBody: VotesSyncBody): BaseResponse<VoteSyncResponse>
}