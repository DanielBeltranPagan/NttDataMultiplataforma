package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val id: Int,
    val contrasena: String
)