package com.example.nttdata.ui.CambiarSucursal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.DTOS.SucursalDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CambiarSucursalScreenModel : ViewModel() {

    // 1. Necesitas definir el httpClient AQUÍ para que lo reconozca
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Esto evita errores si el JSON tiene campos extra
            })
        }
    }

    var listaSucursales by mutableStateOf<List<SucursalDTO>>(emptyList())
    var sucursalActualNombre by mutableStateOf("Castellon de la plana (UJI)")
    var isLoading by mutableStateOf(false)

    init {
        cargarSucursales()
    }

    private fun cargarSucursales() {
        viewModelScope.launch {
            isLoading = true
            try {
                // 2. Ahora httpClient y .body() funcionarán porque tienen los imports correctos
                val response: List<SucursalDTO> = httpClient.get("http://10.0.2.2:8080/api/sucursales").body()
                listaSucursales = response
            } catch (e: Exception) {
                // Es buena idea imprimir el error para saber qué falla (ej: conexión)
                println("Error cargando sucursales: ${e.message}")
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
    fun guardarCambioEnServidor(idUsuario: Int, idSucursal: Int) {
        viewModelScope.launch {
            try {
                // Enviamos un PUT al endpoint de tu API en IntelliJ
                // La URL dependerá de cómo tengas el Controller en Spring Boot
                val response = httpClient.put("http://10.0.2.2:8080/api/usuarios/$idUsuario/cambiar-sucursal") {
                    parameter("idSucursal", idSucursal)
                }

                if (response.status.value in 200..299) {
                    println("¡Guardado en la base de datos con éxito!")
                }
            } catch (e: Exception) {
                println("Error al guardar: ${e.message}")
            }
        }
    }

    fun actualizarSeleccion(nombre: String) {
        sucursalActualNombre = nombre
    }
}