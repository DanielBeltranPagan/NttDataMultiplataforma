package com.example.nttdata.ui.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.DTOS.SucursalDTO // Asegúrate de importar tu SucursalDTO
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
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

// 1. Petición de login
@Serializable
data class LoginRequest(
    val id: Int,
    val contrasena: String
)

// 2. Respuesta del servidor (Corregida para que coincida con UsuarioDTO de Java)
@Serializable
data class LoginResponse(
    @SerialName("idUsuario") // <--- CLAVE: Mapea el 'idUsuario' de Java a esta variable
    val id: Int,
    val contrasena: String? = null,
    val correo: String? = null,
    val rango: String? = "USER",
    val sucursal: SucursalDTO? = null // Recibimos la sucursal para guardarla en sesión
)

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
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
        val idInt = username.toIntOrNull()

        if (idInt == null || password.isBlank()) {
            errorMessage = "Introduce un ID numérico y una contraseña."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                val response: HttpResponse = httpClient.post("http://nttdatabackend-env.eba-uxhfxnfh.us-east-1.elasticbeanstalk.com/api/usuarios/$idInt/validar") {
                    contentType(ContentType.Application.Json)
                    setBody(LoginRequest(id = idInt, contrasena = password))
                }

                if (response.status.value == 200) {
                    val usuarioData = response.body<UsuarioDTO>()

                    // 3. Guardamos los datos REALES en el SessionManager
                    SessionManager.idUsuario = usuarioData.idUsuario
                    SessionManager.correo = usuarioData.correo ?: ""
                    SessionManager.rango = usuarioData.rango ?: "USER"
                    SessionManager.reservasPuestos = usuarioData.reservasPuestos
                    SessionManager.reservasSalas = usuarioData.reservasSalas

                    // Si el usuario ya tiene sucursal, la guardamos también
                    usuarioData.sucursal?.let {
                        SessionManager.nombreSucursal = it.nombre
                    }

                    loginExitoso = true
                } else {
                    errorMessage = "ID o contraseña incorrectos."
                }
            } catch (e: Exception) {
                e.printStackTrace()
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