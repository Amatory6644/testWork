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
        var valid = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && passwordConfirm.isNotBlank()
        Log.d("Valid", "name - $name email - $email password - $password pascon - $passwordConfirm")
        _isFormValid.value = valid
        Log.d("Valid", "_isForm - $_isFormValid")
    }


    private fun observeUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { list ->
                _users.value = list
            }
        }
    }


    fun onSaveClicked(name: String, email: String, password: String, passwordConfirm: String) {
        if ((name.length > 35) || (name.isEmpty())) {
            viewModelScope.launch {
                _toastFlow.emit("Имя должно содержать 1 - 35 символов")
            }
        } else if (email.length > 50 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            viewModelScope.launch {
                _toastFlow.emit("Проверьте корректность Email ")
            }
        }else if ((password.length > 64) || (password.length < 5)) {
            viewModelScope.launch {
                _toastFlow.emit("Пароль должен содержать 5 - 65 символов")
            }
        }else if (passwordConfirm != password) {
            viewModelScope.launch {
                _toastFlow.emit("Пароли не совпадают")
            }
        } else {
            viewModelScope.launch {
                val added = addUserUseCase(name.trim(), email.trim(), password.trim())
                if (added) {
                    _toastFlow.emit("Пользователь сохранён")
                } else {
                    _toastFlow.emit("Пользователь с таким email уже существует")
                }
            }
        }


    }
}

