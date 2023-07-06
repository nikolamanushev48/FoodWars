package com.mentormate.foodwars.ui.registration

import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.Gender
import com.mentormate.foodwars.data.InterestText
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.network.body.UserRegisteredBody
import com.mentormate.foodwars.data.network.model.toUserDetails
import com.mentormate.foodwars.data.network.response.toListOfString
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.data.room.User
import com.mentormate.foodwars.domain.vo.registration.RegistrationUIModel
import com.mentormate.foodwars.domain.vo.usecase.ObtainDataUseCase
import com.mentormate.foodwars.ui.constants.INTEREST
import com.mentormate.foodwars.ui.constants.USER_IMAGE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    val uiState: StateFlow<RegistrationUIModel>
        get() = _uiState
    private val _uiState = MutableStateFlow(RegistrationUIModel())

    init {
        viewModelScope.launch(handler) {
            _uiState.value = RegistrationUIModel(
                subNomenclatures = userRepository.getInterestById(
                    INTEREST,
                    true
                ).response.categories.toListOfString()
            )
        }
    }

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        gender: Gender,
        interest: InterestText
    ) {
        viewModelScope.launch(handler) {
            User(
                firstName = firstName,
                lastName = lastName,
                email = email,
                gender = gender.name,
                interest = interest.number,
                password = password,
                localPicture = USER_IMAGE_URL
            ).let { user ->
                with(userRepository) {
                    register(
                        UserRegisteredBody(
                            true,
                            user.toUserDetails()
                        )
                    ).let {
                        insert(user)
                        ObtainDataUseCase(foodRepository).invoke(it.response.id)
                    }
                }
            }
            _navigation.value = ToDirection(R.id.action_registrationFragment_to_mainActivity)
        }
    }
}