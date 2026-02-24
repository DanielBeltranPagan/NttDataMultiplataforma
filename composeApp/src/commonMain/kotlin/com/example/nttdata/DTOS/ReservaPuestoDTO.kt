package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class ReservaPuestoDTO(
    val idReserva: Int? = null,
    val fecha: String,        // Ejemplo: "2025-12-19"
    val horaInicio: String,   // Ejemplo: "2025-12-19T10:00:00"
    val idPuesto: Int,
    val idUsuario: Int
)