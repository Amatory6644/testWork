package com.example.testwork.data.repository

import com.example.testwork.data.local.dao.UserDao
import com.example.testwork.data.local.entity.UserEntity
import com.example.testwork.data.security.PasswordHasher
import com.example.testwork.domain.model.User
import com.example.testwork.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {

    override fun observeUsers(): Flow<List<User>> {
        return userDao.observeUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addUser(name: String, email: String, password: String): Boolean {
        val existing = userDao.getUserByEmail(email)
        if (existing != null) {
            return false
        }
        val passwordHash = PasswordHasher.hash(password)

        val entity = UserEntity(
            name = name,
            email = email,
            passwordHash = passwordHash
        )
        userDao.insert(entity)
        return true
    }

    private fun UserEntity.toDomain(): User =
        User(
            id = id,
            name = name,
            email = email,
            passwordHash = passwordHash
        )
}

