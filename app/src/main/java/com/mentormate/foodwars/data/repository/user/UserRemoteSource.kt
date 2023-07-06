package com.mentormate.foodwars.data.repository.user

import com.mentormate.foodwars.data.network.body.*
import com.mentormate.foodwars.data.network.model.UserRegistered
import com.mentormate.foodwars.data.network.response.*
import com.mentormate.foodwars.data.network.service.InterestService
import com.mentormate.foodwars.data.network.service.UserService
import com.mentormate.foodwars.data.network.service.VoteService
import javax.inject.Inject

class UserRemoteSource @Inject constructor(
    private val userService: UserService,
    private val voteService: VoteService,
    private val interestService: InterestService
) : UserRepository.UserRemoteSource {

    override suspend fun getUser(userId: Long): BaseResponse<UserResponse> =
        userService.getUser(userId)

    override suspend fun register(userRegistered: UserRegisteredBody): BaseResponse<UserRegistered> =
        userService.register(userRegistered)

    override suspend fun login(userLoginRequest: UserLoginBody): BaseResponse<UserLogin> =
        userService.login(userLoginRequest)

    override suspend fun loginStatus(userId: Long): BaseResponse<UserLoginStatus> =
        userService.loginStatus(userId)

    override suspend fun update(
        userId: Long,
        userUpdateBody: UserUpdateBody
    ): BaseResponse<UserUpdateResponse> =
        userService.update(userId, userUpdateBody)

    override suspend fun logout(userLogoutBody: UserLogoutBody) =
        userService.logout(userLogoutBody)

    override suspend fun clearVotes(userId: Long): BaseResponse<VotesClearedResponse> =
        voteService.clearVotes(userId)

    override suspend fun syncVotes(votesSyncBody: VotesSyncBody): BaseResponse<VoteSyncResponse> =
        voteService.syncVotes(votesSyncBody)

    override suspend fun getInterestById(
        interestId: Int,
        getSubNomenclatures: Boolean
    ): BaseResponse<AllInterestsResponse> =
        interestService.getInterestById(interestId, getSubNomenclatures)
}