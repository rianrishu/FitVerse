package com.rianrishu.fitverse.ui.signup

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rianrishu.fitverse.data.repository.AuthRepository
import com.rianrishu.fitverse.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val validEmailRegex = Regex("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var registerState: Resource<Unit> by mutableStateOf(Resource.Empty())
        private set

    fun onEmailTextChange(email: String) {
        this.email = email
    }

    fun onPasswordTextChange(password: String) {
        this.password = password
    }

    fun onUsernameTextChange(username: String) {
        this.username = username
    }

    private fun verifyUserInput(): Boolean =
        email.matches(validEmailRegex)
                && password.isNotEmpty()
                && username.isNotEmpty()

    fun onSignUpButtonPressed() = viewModelScope.launch {
        if (verifyUserInput()) {
            registerState = Resource.Loading()
            registerState = authRepository.registerUser(
                email,
                username,
                password
            )
        } else {
            registerState = Resource.Error("Enter details correctly")
        }
    }

    fun onSignUpWithGooglePressed(data: Intent) = viewModelScope.launch {
        registerState = Resource.Loading()
        registerState = authRepository.registerUsingGoogle(data = data)
    }
}
