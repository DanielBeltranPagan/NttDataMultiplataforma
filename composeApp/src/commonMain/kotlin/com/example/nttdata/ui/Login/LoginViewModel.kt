package com.example.nttdata.ui.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nttdata.DTOS.LoginDTO
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.DTOS.UsuarioDTO
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

// 1. Petición de login: Ahora enviamos 'correo' como String
@Serializable
data class LoginRequest(
    val correo: String,
    val contrasena: String
)

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("") // Aquí el usuario escribirá su email
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loginExitoso by mutableStateOf(false)

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    fun iniciarSesion() {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Introduce el correo y la contraseña."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                val url = "http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api/usuarios/validar"

                val response: HttpResponse = httpClient.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(LoginDTO(correo = username, contrasena = password))
                }

                if (response.status.value == 200) {
                    val usuarioData = response.body<UsuarioDTO>()

                    // 1. Guardamos los datos básicos
                    SessionManager.idUsuario = usuarioData.idUsuario
                    SessionManager.correo = usuarioData.correo ?: ""
                    SessionManager.rango = usuarioData.rango ?: "USER"

                    // 2. CLAVE: Guardamos las reservas que YA vienen dentro de usuarioData
                    // Asegúrate de que en tu UsuarioDTO estos campos se llamen así
                    usuarioData.reservasPuestos?.let {
                        SessionManager.reservasPuestos = it
                    }
                    usuarioData.reservasSalas?.let {
                        SessionManager.reservasSalas = it
                    }

                    usuarioData.sucursal?.let { suc ->
                        // Si tu SessionManager tiene un campo 'nombreSucursal' (String)
                        SessionManager.nombreSucursal = suc.nombre

                        // O si guarda el objeto completo:
                        // SessionManager.sucursal = suc
                    }

                    loginExitoso = true
                } else {
                    errorMessage = "Correo o contraseña incorrectos."
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
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
        httpClient.close()
    }
}