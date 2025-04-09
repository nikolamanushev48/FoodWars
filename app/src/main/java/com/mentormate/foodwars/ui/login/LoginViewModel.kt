package com.mentormate.foodwars.ui.login

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.ToDirection
import com.mentormate.foodwars.data.network.body.UserLoginBody
import com.mentormate.foodwars.data.network.response.UserLogin
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.repository.user.UserRepository
import com.mentormate.foodwars.domain.vo.usecase.ObtainDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : BaseViewModel(), DefaultLifecycleObserver {

    private fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch(handler) {
            userRepository.run {
                login(
                    UserLoginBody(
                        email,
                        password
                    )
                ).response.let {
                    onLoginSuccessful(it)
                }
            }
        }
    }

    private suspend fun onLoginSuccessful(userLogin: UserLogin) {
//        userRepository.run {
//            insert(getUser(userLogin.userId).response.toUser())
//        }
        if (userLogin.status == "ALREADY_LOGGED_IN" || userLogin.status == "LOGGED_IN") {
            _navigation.value = ToDirection(R.id.action_loginFragment_to_main_screen)
        } else {
            ObtainDataUseCase(foodRepository).invoke(userLogin.userId)
            _navigation.value = ToDirection(R.id.action_loginFragment_to_main_screen)
        }
    }

    fun loginButtonOnClicked(
        email: String,
        password: String
    ) {
        loginUser(email, password)
    }

    fun notRegisteredYetTextClicked() {
        _navigation.value = ToDirection(R.id.action_loginFragment_to_registration_screen)
    }
}