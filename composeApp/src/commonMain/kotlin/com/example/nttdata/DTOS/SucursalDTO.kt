package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class SucursalDTO(
    val id_sucursal: Int? = null,
    val nombre: String,
    val direccion: String? = null
)