package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ActualizarSucursalDTO(
    @SerialName("id_sucursal")
    val idSucursal: Int
)