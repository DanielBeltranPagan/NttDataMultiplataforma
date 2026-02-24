package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SucursalDTO(
    @SerialName("idSucursal") // <--- Esto debe coincidir con el JSON
    val id_sucursal: Int? = null,

    @SerialName("ubicacion")  // <--- En tu JSON NO existe "nombre", existe "ubicacion"
    val nombre: String,

    val direccion: String? = null
)