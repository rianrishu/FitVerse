package com.rishu.fitverse.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishu.fitverse.data.repository.AuthRepository
import com.rishu.fitverse.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

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

    private fun verifyUserInput(): Boolean {
        val regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
        return email.matches(Regex(regex)) && password.isNotEmpty() && username.isNotEmpty()
    }

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

    fun onSignUpWithGooglePressed() = viewModelScope.launch {
        // Handle Google Sign Up
    }
}