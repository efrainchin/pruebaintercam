package com.efrain.intercamprueba.network.repository

import com.efrain.intercamprueba.network.api.BeersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepository @Inject constructor(private val service: BeersService) {

    suspend fun getAllBeers(page: String) =
        withContext(Dispatchers.IO) {
            service.getAllBeers(page)
        }

    suspend fun detailBeer(id: String) =
        withContext(Dispatchers.IO) {
            service.detailBeer(id)
        }
}