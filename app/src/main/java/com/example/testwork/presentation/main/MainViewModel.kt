package com.example.testwork.presentation.main

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwork.domain.model.User
import com.example.testwork.domain.usecase.AddUserUseCase
import com.example.testwork.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    private val _toastFlow = MutableSharedFlow<String>(replay = 0)
    var toastFlow = _toastFlow.asSharedFlow()

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        observeUsers()
    }

    fun onFieldsChanged(name: String, email: String, password: String, passwordConfirm: String) {
        var valid = name.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                passwordConfirm.isNotBlank()
        _isFormValid.value = valid
    }


    private fun observeUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { list ->
                _users.value = list
            }
        }
    }

    private fun validate(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): String? =
        when {
            name.length !in 1..35 ->
                "Имя должно содержать 1 - 35 символов"

            email.length > 50 || !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Проверьте корректность Email"

            password.length !in 5..64 ->
                "Пароль должен содержать 5 - 65 символов"

            passwordConfirm != password ->
                "Пароли не совпадают"

            else -> null
        }


fun onSaveClicked(name: String, email: String, password: String, passwordConfirm: String) {
    val error = validate(name, email, password, passwordConfirm)
    if (error != null) {
        Log.d("ViewModel ", "error - $error")
        viewModelScope.launch { _toastFlow.emit(error) }
        return
    }
    viewModelScope.launch {
        val added = addUserUseCase(name.trim(), email.trim(), password.trim())
        _toastFlow.emit(
            if (added) "Пользователь сохранен"
            else "Пользователь с таким email уже существует"
        )
    }
}


}


