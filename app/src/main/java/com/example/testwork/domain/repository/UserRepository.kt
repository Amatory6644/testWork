package com.example.testwork.domain.repository

import com.example.testwork.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeUsers(): Flow<List<User>>


    suspend fun addUser(name: String, email: String, password: String): Boolean
}

