package com.mentormate.foodwars.utils

import android.text.TextUtils
import android.util.Patterns

object Validators {

    fun isValidEmail(email: String) =
        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidText(text: String) =
        !TextUtils.isEmpty(text)

    fun confirmationValid(
        password: String,
        confirmedPassword: String
    ): Boolean =
        isValidText(password) && password == confirmedPassword

}