package com.example.nttdata.PuestoService

import com.example.nttdata.DTOS.PlantaDTO
import com.example.nttdata.DTOS.PuestoTrabajoDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object PuestoService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true })
        }
    }

    private const val BASE_URL = "http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api"

    suspend fun getPuestosConEstado(idPlanta: Int, fecha: String): List<PuestoTrabajoDTO> {
        return try {
            val response: PlantaDTO = client.get("$BASE_URL/plantas/$idPlanta/estado") {
                url { parameters.append("fecha", fecha) }
            }.body()
            response.puestosTrabajo
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}