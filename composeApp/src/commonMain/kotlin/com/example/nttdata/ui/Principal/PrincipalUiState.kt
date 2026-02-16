package org.example.project.ui.principal // Todo en minúsculas es mejor práctica

data class PrincipalUiState(
    val sucursalActual: String = "Cargando...",
    val mensajeBienvenida: String = "",
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)