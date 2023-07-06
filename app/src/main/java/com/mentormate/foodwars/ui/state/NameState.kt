package com.mentormate.foodwars.ui.state

import com.mentormate.foodwars.R
import com.mentormate.foodwars.utils.Validators

class FirstNameState(val firstName: String? = null) :
    TextFieldState(
        validator = Validators::isValidText,
        errorFor = ::nameErrorValidator
    ) {

    init {
        firstName?.let { text = it }
    }
}

val FirstNameStateSaver = textFieldStateSaver(FirstNameState())

class LastNameState(val lastName: String? = null) :
    TextFieldState(
        validator = Validators::isValidText,
        errorFor = ::nameErrorValidator
    ) {

    init {
        lastName?.let { text = it }
    }
}

val LastNameStateSaver = textFieldStateSaver(LastNameState())

private fun nameErrorValidator() = R.string.invalid_name