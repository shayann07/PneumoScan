package com.devsphere.pneumoscan.presentation.auth

import androidx.lifecycle.ViewModel
import com.devsphere.pneumoscan.domain.usecase.LoginUserUseCase
import com.devsphere.pneumoscan.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUserUseCase,
    private val registerUseCase: RegisterUserUseCase
) : ViewModel() {

    suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        loginUseCase(email, password)
    }

    suspend fun register(email: String, password: String, firstName: String?, lastName: String?) = withContext(Dispatchers.IO) {
        registerUseCase(email, password, firstName, lastName)
    }
}
