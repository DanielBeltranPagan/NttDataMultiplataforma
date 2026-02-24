package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class SalaDTO(
    val idSala: Int? = null,
    val nombre: String,
    val capacidad: Int,
    val disponibilidad: Boolean,
    val id_sucursal: Int? = null
)