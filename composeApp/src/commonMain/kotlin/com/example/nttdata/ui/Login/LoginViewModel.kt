package com.example.nttdata.ui.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nttdata.SesionManager.SessionManager
import kotlinx.coroutines.launch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
@Serializable
data class LoginResponse(
    val id: Int,
    val nombre: String,
    val contrasena: String,
    val correo: String? = null,
    val rango: String? = "USER"
)

class LoginViewModel : ViewModel() {
    // Estados de la UI
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loginExitoso by mutableStateOf(false)

    // Cliente HTTP configurado para JSON
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignora campos que no estén en UsuarioResponse
                prettyPrint = true
                isLenient = true
            })
        }
    }

    fun iniciarSesion() {
        val idInt = username.toIntOrNull()

        if (idInt == null || password.isBlank()) {
            errorMessage = "Introduce un ID numérico y una contraseña."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                // LLAMADA A TU API
                // Nota: 10.0.2.2 es el localhost para el emulador de Android.
                // Si usas iOS o Desktop, usa "localhost" o tu IP local.
                val response: HttpResponse = httpClient.post("http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api/usuarios/$idInt/validar") {
                    contentType(ContentType.Application.Json)
                    setBody(LoginRequest(id = idInt, contrasena = password))
                }

                if (response.status.value == 200) {
                    val usuarioData = response.body<LoginResponse>()

                    // 2. Llenamos el SessionManager con la información del servidor
                    SessionManager.idUsuario = usuarioData.id
                    SessionManager.correo = usuarioData.correo ?: ""
                    SessionManager.rango = usuarioData.rango ?: "USER"

                    // 3. Avisamos a la UI para navegar
                    loginExitoso = true
                } else {
                    errorMessage = "ID o contraseña incorrectos."
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Error de conexión. ¿Está la API encendida?"
            } finally {
                isLoading = false
            }
        }
    }

    fun navegacionCompletada() {
        loginExitoso = false
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close() // Cerramos el cliente al destruir el ViewModel
    }
}