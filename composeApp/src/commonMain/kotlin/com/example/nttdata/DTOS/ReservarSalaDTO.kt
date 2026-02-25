package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class ReservaSalaDTO(
    val idReserva: Int? = null,
    val fecha: String? = "",
    val horaInicio: String? = "",
    val horaFin: String? = "",
    val idSala: Int? = null,
    val idUsuario: Int? = null
)