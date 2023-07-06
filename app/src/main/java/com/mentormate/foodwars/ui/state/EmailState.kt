package com.mentormate.foodwars.ui.state

import com.mentormate.foodwars.R
import com.mentormate.foodwars.utils.Validators

class EmailState(val email: String? = null) :
    TextFieldState(validator = Validators::isValidEmail, errorFor = ::emailValidationError) {
    init {
        email?.let {
            text = it
        }
    }
}

private fun emailValidationError(): Int = R.string.invalid_email

val EmailStateSaver = textFieldStateSaver(EmailState())