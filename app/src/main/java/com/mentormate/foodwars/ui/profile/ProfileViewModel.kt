package com.mentormate.foodwars.ui.profile

import android.net.Uri
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.*
import com.mentormate.foodwars.data.network.body.UserLogoutBody
import com.mentormate.foodwars.data.network.body.UserUpdateBody
import com.mentormate.foodwars.data.network.response.toListOfString
import com.mentormate.foodwars.data.network.response.toUser
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.data.room.User
import com.mentormate.foodwars.domain.vo.profile.ProfileUIModel
import com.mentormate.foodwars.domain.vo.profile.UserUIModel
import com.mentormate.foodwars.ui.constants.INTEREST
import com.mentormate.foodwars.ui.result.ActionResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel(), ProfilePresenter, ActionResultHandler {

    val uiProfileData: LiveData<ProfileUIModel>
        get() = _uiProfileData

    private val _uiProfileData = MutableLiveData(ProfileUIModel())

    init {
        loadUser()
    }

    fun menuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            android.R.id.home -> {
                _navigation.value = PopBack
                true
            }

            else -> false
        }

    private suspend fun setProperties(user: User) {
        user.run {
            _uiProfileData.value =
                ProfileUIModel(

                    UserUIModel(
                        userId,
                        "$firstName $lastName",
                        email,
                        gender
                    ),
//                    subNomenclatures = userRepository.getInterestById(
//                        INTEREST,
//                        true
//                    ).response.categories.toListOfString(),
                    lastSync = lastSyncTime
                )

            uiProfileData.value?.userUIModel?.picture = setPicture(user)

        }
    }

    private fun setPicture(user: User) =
        if (user.localPicture != "") {
            user.localPicture
        } else {
            user.remotePicture
        }

    private fun loadUser() {
        viewModelScope.launch {
            setProperties(userRepository.readCurrentUser())
        }
    }

    private fun getInterest(): InterestText? =
        uiProfileData.value?.interest?.get()?.let { InterestText.values()[it - 1] }

    private suspend fun dataSync(interest: Int) {
        uiProfileData.value?.run {
            userUIModel.id.let { userId ->
                userRepository.getUser(userId).let {
                    with(userRepository) {
                        it.response.let { response ->
                            response.interest = interest
                            response.toUser().let { user ->
                                user.lastSyncTime = Calendar.getInstance().time.toString()
                                lastSync = user.lastSyncTime
                                update(user)
                            }
                            update(
                                userId,
                                UserUpdateBody(
                                    response.password,
                                    response.interest,
                                    response.pictureUrl,
                                    sliderRange.first,
                                    sliderRange.second,
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun changeLocationClicked() {
        _navigation.value = ToDirection(R.id.action_profile_screen_to_changeLocationFragment)
    }

    override fun profilePictureClicked() {
        _navigation.value = ToGallery
    }

    override fun syncButtonClicked() {
        viewModelScope.launch {
            getInterest()?.number?.let { dataSync(it) }
        }
    }

    override fun logoutButtonClicked() {
        viewModelScope.launch {
            try {
                with(userRepository) {
                    logout(UserLogoutBody(readCurrentUser().userId))
                }
                _navigation.value = ToDirection(R.id.action_global_loginScreen)
            } catch (e: Exception) {
                Timber.e("Operation failed! Please check your Internet connection and try again.")
            }
        }
    }

    override fun onSuccess(uri: Uri?) {
        viewModelScope.launch {
            with(userRepository) {
                readCurrentUser().let {
                    it.localPicture = uri.toString()
                    update(it)
                }
            }

            uiProfileData.value?.userUIModel?.picture = uri.toString()
        }
    }
}