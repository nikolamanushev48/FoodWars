package com.mentormate.foodwars.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.*
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.domain.vo.location.ChangeLocationUIModel
import com.mentormate.foodwars.ui.permission.PermissionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLocationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : BaseViewModel(), ChangeLocationPresenter, PermissionHandler {

    val uiChangeLocationData: LiveData<ChangeLocationUIModel>
        get() = _uiChangeLocationData

    private val _uiChangeLocationData = MediatorLiveData<ChangeLocationUIModel>().apply {
        value = ChangeLocationUIModel()
    }

    private fun setDeniedState() {
        _uiChangeLocationData.value =
            ChangeLocationUIModel(
                emotionFaceImageResource = R.drawable.sad_face,
                emotionStateTextSizeResource = R.dimen.sadStateTextSize,
                emotionFaceImageViewVisibility = true,
                emotionStateTextVisibility = true,
                settingsButtonVisibility = true,
                changeButtonVisibility = false,
                descriptionTextViewVisibility = false,
                motivationTextViewVisibility = false
            )
    }

    private fun startPermissionCheck() {
        uiChangeLocationData.value?.animationViewVisibility = true
        _navigation.value = CheckPermissions
    }

    private suspend fun setAllowedState() {
        _uiChangeLocationData.value =
            ChangeLocationUIModel(
                emotionFaceImageResource = R.drawable.happy_face,
                emotionStateTextResource = R.string.get_location_success_text,
                emotionStateTextSizeResource = R.dimen.happyStateTextSize,
                settingsButtonTextResource = R.string.done,
                numberOfFoods = foodRepository.getAllFoodsWithFlow().first().size
            )
        setDeniedState()
    }

    override fun changeLocationButtonClicked() {
        startPermissionCheck()
    }

    override fun settingsButtonClicked() {
        if (uiChangeLocationData.value?.emotionFaceImageResource == R.drawable.happy_face) {
            _navigation.value = PopBack
        } else {
            _navigation.value = ToSettings()
        }
    }

    override fun onSuccess(locationLiveData: LocationLiveData, checkAvailableAddress: Boolean) {
        if (checkAvailableAddress) {
            _uiChangeLocationData.addSource(locationLiveData) {
                //getting address later
                viewModelScope.launch {
                    setAllowedState()
                }
            }
        } else {
            _dialog.value = MessageDialog(
                messageTextResource = R.string.alert_message,
                positiveButtonCallback = { startPermissionCheck() },
                positiveButtonTextRes = R.string.positive_button_alert,
                negativeButtonTextRes = R.string.negative_button_alert
            )
        }

        viewModelScope.launch {
            foodRepository.resetVotes()
            with(userRepository) {
                clearVotes(readCurrentUser().userId)
            }
        }
    }

    override fun onError(argument: Any?) {
        super.onError(argument)

        _dialog.value = MessageDialog(
            R.string.alert_message,
            positiveButtonCallback = { startPermissionCheck() },
            negativeButtonCallback = { setDeniedState() }
        )
    }
}