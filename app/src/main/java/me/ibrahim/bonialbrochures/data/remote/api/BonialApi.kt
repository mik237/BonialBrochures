package me.ibrahim.bonialbrochures.data.remote.api

import me.ibrahim.bonialbrochures.data.remote.dto.BrochuresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BonialApi {

    @GET("shelf.json")
    suspend fun getBrochures(
        @Query("number") pageNumber: Int,
    ): BrochuresResponse
}