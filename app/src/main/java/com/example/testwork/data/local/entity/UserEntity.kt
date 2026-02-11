package com.example.testwork.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "password")
    val password: String,
    @PrimaryKey()
    @ColumnInfo(name = "email")
    val email: String

)

