package com.peterfam.valifaysdk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    val userName: String,
    val password: String,
    val mob: Int,
    val email: String,
    val imgUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
