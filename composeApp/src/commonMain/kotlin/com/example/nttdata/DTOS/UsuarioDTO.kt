package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UsuarioDTO(
    val idUsuario: Int? = null,
    val correo: String,
    val rango: String,
    val sucursal: SucursalDTO? = null,
    // Cambia el nombre entre comillas por el que uses en tu clase Java de IntelliJ
    @SerialName("reservasPuestos")
    val reservasPuestos: List<ReservaPuestoDTO> = emptyList(),
    @SerialName("reservasSalas")
    val reservasSalas: List<ReservaSalaDTO> = emptyList()
)