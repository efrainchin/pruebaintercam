package com.efrain.intercamprueba.network.api

import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.entities.EntityDetailBeer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeersService {

    @GET("beers")
    suspend fun getAllBeers(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "10"
    ): Response<List<EntityBeers>>

    @GET("beers/{id}")
    suspend fun detailBeer(
        @Path("id") id: String
    ): Response<List<EntityDetailBeer>>
}