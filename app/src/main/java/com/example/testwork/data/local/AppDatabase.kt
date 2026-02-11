package com.example.testwork.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testwork.data.local.dao.UserDao
import com.example.testwork.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}

