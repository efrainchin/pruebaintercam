package com.efrain.intercamprueba.di

import android.content.Context
import androidx.room.Room
import com.efrain.intercamprueba.data.IntercamDataBase
import com.efrain.intercamprueba.data.dao.BeersDao
import com.efrain.intercamprueba.data.dao.UsersDao
import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.util.Contants.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext context: Context): IntercamDataBase =
        Room.databaseBuilder(context, IntercamDataBase::class.java, DB_NAME)
            .allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun providesBeersDao(dataBase: IntercamDataBase): BeersDao
        = dataBase.getBeersDao()

    @Provides
    fun providesBeers(beersDao: BeersDao): List<EntityBeers>
        = beersDao.getAllBeers()

    @Provides
    @Singleton
    fun providesUsersDao(dataBase: IntercamDataBase): UsersDao
            = dataBase.getUserDao()
}