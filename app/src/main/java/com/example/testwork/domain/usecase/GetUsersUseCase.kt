package com.example.testwork.domain.usecase

import com.example.testwork.domain.model.User
import com.example.testwork.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(): Flow<List<User>> = repository.observeUsers()
}

