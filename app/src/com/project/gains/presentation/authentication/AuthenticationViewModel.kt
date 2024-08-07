package com.project.gains.presentation.authentication

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import com.project.gains.presentation.authentication.events.SignInEvent
import com.project.gains.presentation.authentication.events.SignUpEvent

import com.project.gains.data.manager.UpdateListener
import com.project.gains.domain.usecase.auth.AuthenticationUseCases
import com.project.gains.presentation.authentication.events.OTPEvent
import com.project.gains.util.Constants.LOGIN_SUCCESS
import com.project.gains.util.Constants.SIGN_UP_SUCCESS
import com.project.gains.util.Constants.USER_AUTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

/* Class responsible for handling authentication related events. It relies on the appEntryUseCases dependency
* to perform operations related to saving the app entry. Notice that the latter class is injected using hilt
 */
@HiltViewModel
class AuthenticationViewModel  @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases
): ViewModel(), UpdateListener {

    private val _data = MutableLiveData<String?>()
    val data: MutableLiveData<String?> = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _navigateToAnotherScreen = MutableLiveData<Boolean>()
    val navigateToAnotherScreen: LiveData<Boolean> = _navigateToAnotherScreen

    private val _otp = MutableLiveData<Int>()
    val otp: MutableLiveData<Int> = _otp

    init {
        // Set ViewModel as the listener for updates
        authenticationUseCases.subscribe.invoke(this,USER_AUTH)
    }
    fun onSignInEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> {
                _isError.value = false
                _isLoading.value = true

                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    goSignIn(event.email, event.password)
                }
            }
        }
    }

    fun onSignUpEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> {
                _isError.value = false
                _isLoading.value = true

                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    goSignUp(event.name, event.email, event.password, event.confirmPass)
                }
            }
        }
    }

    private fun goSignIn(email: String, password: String) {
        viewModelScope.launch {
            authenticationUseCases.signIn(email, password)
        }
    }

    fun onOTPEvent(event: OTPEvent) {
        when(event) {
            is OTPEvent.GenerateOTP -> {
                _otp.value = Random.nextInt(0, 1000)
            }
        }
    }

    private fun goSignUp(name: String, email: String, password: String, confirmPass: String) {
        viewModelScope.launch {
            authenticationUseCases.signUp(name, email, password, confirmPass)
        }
    }

    override fun onUpdate(data: Location) {
    }

    override fun onUpdate(data: String) {
        // Update UI state with received data
        _data.value = data
        if (_data.value == SIGN_UP_SUCCESS || _data.value == LOGIN_SUCCESS) {
            _isLoading.value = false
            _isError.value = false
            _navigateToAnotherScreen.value = true
        } else {
            _isLoading.value = false
            _isError.value = true
        }
    }

    fun onNavigationComplete() {
        _navigateToAnotherScreen.value = false
        _data.value = ""
    }
}