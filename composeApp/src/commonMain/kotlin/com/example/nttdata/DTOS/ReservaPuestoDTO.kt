package com.example.nttdata.DTOS

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservaPuestoDTO(
    val idReserva: Int? = null,
    val fecha: String? = null,
    val horaInicio: String? = null,
    val horaFin: String? = null,
    val idPuesto: Int? = null,
    val idUsuario: Int? = null
)