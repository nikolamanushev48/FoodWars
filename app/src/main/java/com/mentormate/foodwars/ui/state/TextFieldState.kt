package com.mentormate.foodwars.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import com.mentormate.foodwars.ui.constants.INVALID_RES
import kotlin.reflect.KFunction0

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor: () -> Int = { INVALID_RES }
) {
    var text: String by mutableStateOf("")

    // was the TextField ever focused
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError() =
        errorFor().takeIf { showErrors() }
}

fun textFieldStateSaver(state: TextFieldState) = listSaver<TextFieldState, Any>(
    save = { listOf(it.text, it.isFocusedDirty) },
    restore = {
        state.apply {
            text = it[0] as String
            isFocusedDirty = it[1] as Boolean
        }
    }
)