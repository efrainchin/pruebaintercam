package com.efrain.intercamprueba.di

import com.efrain.intercamprueba.network.api.BeersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ServicesModule {
    @Provides
    @Singleton
    fun provideAuthContactService( retrofit: Retrofit): BeersService =
        retrofit.create(BeersService::class.java)
}