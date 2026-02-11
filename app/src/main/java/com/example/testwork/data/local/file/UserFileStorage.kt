package com.example.testwork.data.local.file

import android.content.Context
import com.example.testwork.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class UserFileStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val fileName = "users.txt"

    suspend fun appendUser(user: User) {
        val line = buildString {
            append("name=")
            append(user.name)
            append("; email=")
            append(user.email)
            append("; password=")
            append(user.password)
            append('\n')
        }

        withContext(Dispatchers.IO) {
            context.openFileOutput(fileName, Context.MODE_APPEND).use { fos ->
                fos.write(line.toByteArray(Charsets.UTF_8))
            }
        }
    }
}

