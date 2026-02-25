package com.example.nttdata.ReservaService

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ReservaService {

    private val client = HttpClient {
        // Simplemente 'install', sin el prefijo HttpClientConfig
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    private const val BASE_URL = "http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api"

    suspend fun eliminarReservaPuesto(idReserva: Int): Boolean {
        return try {
            val response = client.delete("$BASE_URL/reservas-puestos/$idReserva")
            response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun eliminarReservaSala(idReserva: Int): Boolean {
        return try {
            val response = client.delete("$BASE_URL/reservas-salas/$idReserva")
            response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}