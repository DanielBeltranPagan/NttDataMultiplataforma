package com.example.nttdata.ui.CambiarSucursal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.DTOS.ActualizarSucursalDTO
import com.example.nttdata.DTOS.SucursalDTO
import com.example.nttdata.SesionManager.SessionManager // Importamos el SessionManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CambiarSucursalScreenModel : ViewModel() {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    var listaSucursales by mutableStateOf<List<SucursalDTO>>(emptyList())

    // 1. CAMBIO: Ahora el estado inicial se toma del SessionManager.
    // Si está vacío, ponemos un texto por defecto o "Sin sucursal".
    var sucursalActualNombre by mutableStateOf(SessionManager.nombreSucursal ?: "Seleccione una sucursal")

    var isLoading by mutableStateOf(false)

    init {
        cargarSucursales()
        // 2. Aseguramos que al abrir la pantalla se use el nombre guardado en la sesión
        actualizarDesdeSesion()
    }

    private fun actualizarDesdeSesion() {
        val nombreEnSesion = SessionManager.nombreSucursal
        if (!nombreEnSesion.isNullOrBlank()) {
            sucursalActualNombre = nombreEnSesion
        }
    }

    private fun cargarSucursales() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response: List<SucursalDTO> = httpClient.get("http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api/sucursales").body()
                listaSucursales = response
            } catch (e: Exception) {
                println("Error cargando sucursales: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }


    fun guardarCambioEnServidor(idUsuario: Int, idSucursal: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                isLoading = true
                val url = "http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api/usuarios/$idUsuario/sucursal"

                val response = httpClient.put(url) {
                    contentType(ContentType.Application.Json)
                    setBody(ActualizarSucursalDTO(idSucursal = idSucursal))
                }

                if (response.status.value in 200..299) {
                    // 3. ¡IMPORTANTE! Actualizamos el SessionManager para que el cambio persista
                    // Esto se suele hacer aquí o en el onClick del botón antes del pop()
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun actualizarSeleccion(nombre: String) {
        sucursalActualNombre = nombre
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }
}