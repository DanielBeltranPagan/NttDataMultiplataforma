package com.example.nttdata.ReservaService

import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.DTOS.ReservaSalaDTO
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
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
    suspend fun crearReservaPuesto(reserva: ReservaPuestoDTO){
        return try {
            val response = client.post("$BASE_URL/reservas-puestos"){
                contentType(ContentType.Application.Json)
                setBody(reserva)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    suspend fun modificarReservaPuesto(idReserva: Int, reserva: ReservaPuestoDTO): Boolean {
        return try {
            val response = client.put("$BASE_URL/reservas-puestos/$idReserva") {
                contentType(ContentType.Application.Json)
                setBody(reserva)
            }
            response.status.value in 200..299
        } catch (e: Exception) { false }
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

    suspend fun modificarReservaSala(idReserva: Int, reserva: ReservaSalaDTO): Boolean {
        return try {
            val response = client.put("$BASE_URL/reservas-salas/$idReserva") {
                contentType(ContentType.Application.Json)
                setBody(reserva)
            }
            response.status.value in 200..299
        } catch (e: Exception) { false }
    }
}
