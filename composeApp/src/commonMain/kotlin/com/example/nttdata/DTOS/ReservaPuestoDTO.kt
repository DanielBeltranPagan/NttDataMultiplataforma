package com.example.nttdata.DTOS

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservaPuestoDTO(
    @SerialName("id_reserva")
    val idReserva: Int? = null,

    val fecha: String? = "",

    @SerialName("hora_inicio")
    val horaInicio: String? = "", // Quitamos el guion medio del nombre de la variable por convención

    @SerialName("hora_fin")
    val horaFin: String? = "",

    @SerialName("id_puesto")
    val idPuesto: Int? = null,

    @SerialName("id_usuario")
    val idUsuario: Int? = null
)