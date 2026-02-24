package com.example.nttdata.ui.GestionarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.DTOS.ReservaSalaDTO
import com.example.nttdata.DTOS.UsuarioDTO
import com.example.nttdata.SesionManager.SessionManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
class ReservasViewModel : ViewModel() {
    var listaReservas by mutableStateOf<List<ReservaPuestoDTO>>(emptyList())
    var isLoading by mutableStateOf(false)

    private val httpClient = HttpClient {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }

    init { cargarReservas() }

    fun cargarReservas() {
        val userId = SessionManager.idUsuario ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                val usuario: UsuarioDTO = httpClient
                    .get("http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api/usuarios/$userId")
                    .body()

                // Log para debuguear en la pestaña 'Logcat'
                println("DEBUG: Reservas recibidas: ${usuario.reservasPuestos.size}")

                listaReservas = usuario.reservasPuestos
            } catch (e: Exception) {
                println("DEBUG: Error cargando: ${e.message}")
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}