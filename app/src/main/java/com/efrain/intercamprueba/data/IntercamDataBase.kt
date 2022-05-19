package com.efrain.intercamprueba.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.efrain.intercamprueba.models.UserModel
import com.efrain.intercamprueba.data.dao.BeersDao
import com.efrain.intercamprueba.data.dao.UsersDao
import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.util.Contants.Companion.DB_VERSION

@Database(entities = [EntityBeers::class, UserModel::class], version = DB_VERSION, exportSchema = false)
abstract class IntercamDataBase: RoomDatabase() {

    abstract fun getBeersDao(): BeersDao

    abstract fun getUserDao(): UsersDao

}