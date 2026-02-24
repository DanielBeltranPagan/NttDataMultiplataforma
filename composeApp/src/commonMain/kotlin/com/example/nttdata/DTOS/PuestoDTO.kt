package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class PuestoDTO(
    val idPuesto: Int? = null,
    val codigo: String,
    val disponibilidad: Boolean,
    val id_sucursal: Int? = null
)