package org.example.project.ui.principal

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PrincipalScreenModel : StateScreenModel<PrincipalUiState>(PrincipalUiState()) {

    init {
        // Llamamos a la carga de datos al iniciar
        cargarDatosIniciales()
    }

    private fun cargarDatosIniciales() {
        screenModelScope.launch {
            mutableState.update { it.copy(isLoading = true) }

            try {
                // Simulamos una carga de red
                delay(1500)

                mutableState.update {
                    it.copy(
                        sucursalActual = "Sucursal Central NTT",
                        mensajeBienvenida = "¡Hola! ¿Qué deseas hacer hoy?",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                mutableState.update {
                    it.copy(isLoading = false, errorMessage = "Error al conectar")
                }
            }
        }
    }
}