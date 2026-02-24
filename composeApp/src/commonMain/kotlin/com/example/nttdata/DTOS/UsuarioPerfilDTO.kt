package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class  UsuarioPerfilDTO(
    val idUsuario: Int,
    val correo: String,
    val nombreSucursal: String,
    val rango: String
)