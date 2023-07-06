package com.mentormate.foodwars.data.repository.user

import com.mentormate.foodwars.data.room.User
import com.mentormate.foodwars.data.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val userDao: UserDao,
) : UserRepository.UserLocalSource {

    override suspend fun insert(value: User) {
        userDao.insert(value)
    }

    override suspend fun isUserAvailable(): Boolean =
        userDao.getCurrentUser() != null

    override suspend fun readCurrentUser() = userDao.getAll().first()

    override suspend fun update(user: User) = userDao.update(user)

    override fun getCurrentUserWithFlow(): Flow<User> = userDao.getCurrentUserWithFlow()
}