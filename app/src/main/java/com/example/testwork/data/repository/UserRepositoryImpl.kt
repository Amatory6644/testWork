package com.example.testwork.data.repository

import com.example.testwork.data.local.dao.UserDao
import com.example.testwork.data.local.entity.UserEntity
import com.example.testwork.data.local.file.UserFileStorage
import com.example.testwork.domain.model.User
import com.example.testwork.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userFileStorage: UserFileStorage
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

        val entity = UserEntity(
            name = name,
            email = email,
            password = password
        )
        userDao.insert(entity)

        val user = entity.toDomain()
        userFileStorage.appendUser(user)


        return true
    }

    private fun UserEntity.toDomain(): User =
        User(
            name = name,
            email = email,
            password = password
        )
}

