package com.example.testwork.domain.usecase

import com.example.testwork.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository
) {


    suspend operator fun invoke(name: String, email: String, password: String): Boolean {
        return repository.addUser(name, email, password)
    }
}

