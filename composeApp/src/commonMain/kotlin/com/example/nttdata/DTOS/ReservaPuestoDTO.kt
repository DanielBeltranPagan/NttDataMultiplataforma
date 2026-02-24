package com.example.nttdata.DTOS

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservaPuestoDTO(
    @SerialName("idReserva")
    val idReserva: Int? = null,

    @SerialName("fecha")
    val fecha: String,

    @SerialName("horaInicio") // Asegúrate de que coincida con el "private LocalDateTime horaInicio" de Java
    val horaInicio: String,

    @SerialName("horaFin")
    val horaFin: String,

    @SerialName("idPuesto")
    val idPuesto: Int,

    @SerialName("idUsuario")
    val idUsuario: Int
)