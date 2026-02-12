package com.example.testwork

import android.util.Log
import com.example.testwork.data.repository.UserRepositoryImpl


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testwork.data.local.AppDatabase
import com.example.testwork.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRepositoryImplTest {

    private lateinit var db: AppDatabase
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        repository = UserRepositoryImpl(db.userDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addUser saves user with hashed password and enforces unique email`() = runBlocking {
        val email = "bob@example.com"
        val password = "123456"

        val first = repository.addUser("User1", email, password)
        val second = repository.addUser("User2", email, password)

        assertTrue(first)
        assertFalse(second)

        val users = repository.observeUsers().first()
        Log.d("test","users - $users")

        assertEquals(1, users.size)
        val user = users.first()
        Log.d("test","user - $user")
        assertEquals(email, user.email)
        assertNotEquals(password, user.passwordHash)
    }
}