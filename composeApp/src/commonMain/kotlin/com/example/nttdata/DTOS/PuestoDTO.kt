package com.example.nttdata.DTOS

import kotlinx.serialization.Serializable

@Serializable
data class PuestoTrabajoDTO(
    val idPuesto: Int,
    val tieneOrdenador: Boolean? = null,
    val idPlanta: Int? = null,
    val ocupado: Boolean = false
)

@Serializable
data class PlantaDTO(
    val idPlanta: Int,
    val puestosTrabajo: List<PuestoTrabajoDTO> = emptyList()
)