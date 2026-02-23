package com.example.nttdata.ui.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// 1. Esta es la CLASE que se marca como Serializable
// Es el "molde" que el traductor usará para crear el JSON
@Serializable
data class LoginRequest(
    val id: Int,
    val contrasena: String
)

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loginExitoso by mutableStateOf(false)

    // 2. Configuramos el cliente con el "Negociador"
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun iniciarSesion() {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, rellena todos los campos."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                val exito = validarEnServidor(username.toInt(), password)
                if (exito) {
                    loginExitoso = true
                } else {
                    errorMessage = "Usuario o contraseña incorrectos"
                }
            } catch (e: Exception) {
                errorMessage = "Error al conectar con el servidor"
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun validarEnServidor(id: Int, contrasena: String): Boolean {
        return try {
            val baseUrl = "http://10.0.2.2:8080"

            // CORRECCIÓN: Añadimos el /id/ en la URL
            val response: HttpResponse = httpClient.post("$baseUrl/api/usuarios/$id/validar") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(id, contrasena))
            }

            response.status.value == 200
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun navegacionCompletada() {
        loginExitoso = false
    }
}