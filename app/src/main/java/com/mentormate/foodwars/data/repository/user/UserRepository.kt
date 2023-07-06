package com.mentormate.foodwars.data.repository.user

import com.mentormate.foodwars.data.network.body.*
import com.mentormate.foodwars.data.network.model.UserRegistered
import com.mentormate.foodwars.data.network.response.*
import com.mentormate.foodwars.data.room.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class UserRepository @Inject constructor(
    private var localSource: UserLocalSource,
    private var remoteSource: UserRemoteSource
) {

    interface UserLocalSource {
        suspend fun insert(value: User)

        suspend fun isUserAvailable(): Boolean

        suspend fun readCurrentUser(): User

        suspend fun update(user: User)

        fun getCurrentUserWithFlow(): Flow<User>
    }

    interface UserRemoteSource {

        suspend fun getUser(userId: Long): BaseResponse<UserResponse>

        suspend fun register(userRegistered: UserRegisteredBody): BaseResponse<UserRegistered>

        suspend fun login(userLoginRequest: UserLoginBody): BaseResponse<UserLogin>

        suspend fun loginStatus(userId: Long): BaseResponse<UserLoginStatus>

        suspend fun update(
            userId: Long,
            userUpdateBody: UserUpdateBody
        ): BaseResponse<UserUpdateResponse>

        suspend fun logout(userLogoutBody: UserLogoutBody)
        
        suspend fun clearVotes(userId: Long): BaseResponse<VotesClearedResponse>

        suspend fun syncVotes(votesSyncBody: VotesSyncBody): BaseResponse<VoteSyncResponse>

        suspend fun getInterestById(interestId: Int, getSubNomenclatures: Boolean): BaseResponse<AllInterestsResponse>
    }

    suspend fun insert(value: User) {
        localSource.insert(value)
    }

    suspend fun isUserAvailable(): Boolean = localSource.isUserAvailable()

    suspend fun readCurrentUser() = localSource.readCurrentUser()

    suspend fun getUser(userId: Long): BaseResponse<UserResponse> =
        remoteSource.getUser(userId)

    suspend fun register(userRegistered: UserRegisteredBody): BaseResponse<UserRegistered> =
        remoteSource.register(userRegistered)

    suspend fun login(userLoginRequest: UserLoginBody): BaseResponse<UserLogin> =
        remoteSource.login(userLoginRequest)

    suspend fun loginStatus(userId: Long): BaseResponse<UserLoginStatus> =
        remoteSource.loginStatus(userId)

    fun getCurrentUserWithFlow(): Flow<User> = localSource.getCurrentUserWithFlow()

    suspend fun update(user: User) = localSource.update(user)

    suspend fun update(
        userId: Long,
        userUpdateBody: UserUpdateBody
    ) = remoteSource.update(userId, userUpdateBody)

    suspend fun logout(userLogoutBody: UserLogoutBody) =
        remoteSource.logout(userLogoutBody)

    suspend fun clearVotes(userId: Long): BaseResponse<VotesClearedResponse> =
        remoteSource.clearVotes(userId)

    suspend fun syncVotes(votesSyncBody: VotesSyncBody): BaseResponse<VoteSyncResponse> =
        remoteSource.syncVotes(votesSyncBody)

    suspend fun getInterestById(interestId: Int, getSubNomenclatures: Boolean): BaseResponse<AllInterestsResponse> = remoteSource.getInterestById(interestId,getSubNomenclatures)
}