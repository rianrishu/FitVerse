package com.rianrishu.fitverse.ui.signin

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
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val validEmailRegex = Regex("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var registerState: Resource<Unit> by mutableStateOf(Resource.Empty())
        private set

    fun onEmailTextChange(email: String) {
        this.email = email
    }

    fun onPasswordTextChange(password: String) {
        this.password = password
    }

    private fun verifyUserInput(): Boolean =
        email.matches(validEmailRegex)
                && password.isNotEmpty()

    fun onSignInButtonPressed() = viewModelScope.launch {
        if (verifyUserInput()) {
            registerState = Resource.Loading()
            registerState = authRepository.loginUser(
                email,
                password
            )
        } else {
            registerState = Resource.Error("Enter details correctly")
        }
    }

    fun onSignInWithGooglePressed(data: Intent) = viewModelScope.launch {
        registerState = Resource.Loading()
        registerState = authRepository.loginUsingGoogle(data = data)
    }
}
