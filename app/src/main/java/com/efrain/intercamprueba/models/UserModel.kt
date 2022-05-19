package com.efrain.intercamprueba.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efrain.intercamprueba.util.Contants.Companion.TABLE_USERS

@Entity(tableName = TABLE_USERS)
data class UserModel (
    @PrimaryKey
    val email: String,
    val password: String
    )