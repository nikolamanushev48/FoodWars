package com.mentormate.foodwars.ui.state

import com.mentormate.foodwars.R
import com.mentormate.foodwars.utils.Validators

class PasswordState :
    TextFieldState(validator = Validators::isValidText, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) :
    TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError() =
        passwordConfirmationError().takeIf { showErrors() }
}

private fun passwordAndConfirmationValid(
    password: String,
    confirmedPassword: String
) = isPasswordValid(password) && password == confirmedPassword

private fun isPasswordValid(password: String) = password.length > 3

private fun passwordConfirmationError() = R.string.passwords_don_t_match

private fun passwordValidationError() = R.string.invalid_password