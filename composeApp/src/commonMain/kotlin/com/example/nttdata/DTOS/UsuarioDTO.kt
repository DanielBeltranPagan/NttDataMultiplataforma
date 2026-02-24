package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDTO(
    val idUsuario: Int? = null,
    val contrasena: String? = null,
    val correo: String,
    val rango: String,
    val sucursal: SucursalDTO? = null
)