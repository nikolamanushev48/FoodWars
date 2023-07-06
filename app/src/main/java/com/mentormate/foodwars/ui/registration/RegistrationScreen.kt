package com.mentormate.foodwars.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.Gender
import com.mentormate.foodwars.data.InterestText
import com.mentormate.foodwars.ui.login.AppLogo
import com.mentormate.foodwars.ui.login.Email
import com.mentormate.foodwars.ui.login.Password
import com.mentormate.foodwars.ui.login.TextFieldError
import com.mentormate.foodwars.ui.state.ConfirmPasswordState
import com.mentormate.foodwars.ui.state.EmailState
import com.mentormate.foodwars.ui.state.EmailStateSaver
import com.mentormate.foodwars.ui.state.FirstNameState
import com.mentormate.foodwars.ui.state.FirstNameStateSaver
import com.mentormate.foodwars.ui.state.LastNameState
import com.mentormate.foodwars.ui.state.LastNameStateSaver
import com.mentormate.foodwars.ui.state.PasswordState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel = hiltViewModel()) {
    val registrationState by viewModel.uiState.collectAsState()

    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }
    val passwordState = remember { PasswordState() }

    val firstNameState by rememberSaveable(stateSaver = FirstNameStateSaver) {
        mutableStateOf(FirstNameState())
    }
    val lastNameState by rememberSaveable(stateSaver = LastNameStateSaver) {
        mutableStateOf(LastNameState())
    }
    val confirmPasswordState =
        remember { ConfirmPasswordState(passwordState = passwordState) }
    val gender = rememberSaveable { mutableStateOf(Gender.OTHER) }
    val interest = rememberSaveable { mutableStateOf(InterestText.BULGARIAN) }
    val focusRequester = remember { FocusRequester() }
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.White,
        cursorColor = Color.White,
        focusedLabelColor = Color.White,
        textColor = Color.White,
        unfocusedLabelColor = Color.White,
        unfocusedBorderColor = Color.White
    )

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.main_background),
                contentScale = ContentScale.FillBounds
            )
            .verticalScroll(scrollState, reverseScrolling = true)
    ) {
        AppLogo()

        TextField(
            value = firstNameState.text,
            onValueChange = {
                firstNameState.text = it
            },
            label = {
                Text(
                    text = stringResource(id = R.string.first_name),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    firstNameState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        firstNameState.enableShowErrors()
                    }
                }
                .padding(vertical = 2.dp, horizontal = 20.dp),
            colors = colors,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = firstNameState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.requestFocus()
                }
            )
        )

        firstNameState.getError()
            ?.let { error -> TextFieldError(textError = stringResource(id = error)) }

        OutlinedTextField(
            value = lastNameState.text,
            onValueChange = {
                lastNameState.text = it
            },
            label = {
                Text(
                    text = stringResource(id = R.string.last_name),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    lastNameState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        lastNameState.enableShowErrors()
                    }
                }
                .padding(vertical = 2.dp, horizontal = 20.dp),
            colors = colors,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = lastNameState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.requestFocus()
                }
            ),
        )

        lastNameState.getError()
            ?.let { error -> TextFieldError(textError = stringResource(id = error)) }

        Email(emailState, colors = colors, onImeAction = { focusRequester.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))

        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            colors = colors,
            modifier = Modifier.focusRequester(focusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val showPassword = rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            value = confirmPasswordState.text,
            onValueChange = {
                confirmPasswordState.text = it
                confirmPasswordState.enableShowErrors()
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    confirmPasswordState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        confirmPasswordState.enableShowErrors()
                    }
                }
                .padding(horizontal = 20.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = stringResource(id = R.string.confirm_password),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            colors = colors,
            trailingIcon = {
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    if (showPassword.value) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = stringResource(id = R.string.pass_hidden),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(id = R.string.pass_shown),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            visualTransformation = if (showPassword.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = passwordState.showErrors(),
            supportingText = {
                passwordState.getError()
                    ?.let { error -> TextFieldError(textError = stringResource(id = error)) }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.requestFocus()
                }
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        RadioBox(gender = gender)

        Spacer(modifier = Modifier.height(16.dp))

        Interests(
            interest = interest,
            subNomenclatures = registrationState.subNomenclatures
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.registerUser(
                    firstNameState.text,
                    lastNameState.text,
                    emailState.text,
                    passwordState.text,
                    gender.value,
                    interest.value
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 80.dp),
            enabled = firstNameState.isValid &&
                    lastNameState.isValid &&
                    emailState.isValid &&
                    passwordState.isValid &&
                    confirmPasswordState.isValid,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Interests(
    interest: MutableState<InterestText>,
    subNomenclatures: List<String>,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            TextField(
                value = stringResource(id = interest.value.stringResource),
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(id = R.string.interests),
                        color = Color.White
                    )
                },

                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier.menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                subNomenclatures.forEach { selectedOption ->
                    DropdownMenuItem(onClick = {
                        interest.value = InterestText.valueOf(selectedOption.uppercase())
                        expanded = false
                    },
                        text = {
                            Text(
                                text = selectedOption
                            )
                        })
                }
            }
        }
    }
}

@Composable
fun RadioBox(gender: MutableState<Gender>) {
    Column(
        modifier = Modifier.selectableGroup()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(
                    selected = (gender.value.stringResource == Gender.MAN.stringResource),
                    onClick = {}
                )
                .padding(horizontal = 16.dp)
        ) {
            Gender.values().forEach {
                RadioButton(
                    selected = (gender.value.stringResource == it.stringResource),
                    onClick = { gender.value = it },
                    colors = RadioButtonDefaults.colors(Color.Green)
                )
                Text(
                    text = stringResource(id = it.stringResource),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
