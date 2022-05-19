package com.efrain.intercamprueba.data.dao

import androidx.room.*
import com.efrain.intercamprueba.models.UserModel

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUser(vararg users: UserModel)

    @Delete
    suspend fun delete(user: UserModel)
}