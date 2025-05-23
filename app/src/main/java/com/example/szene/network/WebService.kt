package com.example.myapplication.network


import com.example.myapplication.network.response.PeliculasResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface WebService {
    @GET("now_playing")
    suspend fun obtenerCartelera(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("popular")
    suspend fun obtenerPopulares(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("top_rated")
    suspend fun obtenerTop(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>
}