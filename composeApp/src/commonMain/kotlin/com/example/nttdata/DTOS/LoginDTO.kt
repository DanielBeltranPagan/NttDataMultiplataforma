package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val correo: String, // Cambiado de id: Int a correo: String
    val contrasena: String
)