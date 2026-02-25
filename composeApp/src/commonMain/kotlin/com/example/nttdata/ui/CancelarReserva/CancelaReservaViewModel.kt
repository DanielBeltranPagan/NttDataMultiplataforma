package com.example.nttdata.ui.CancelarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.ReservaService.ReservaService
import com.example.nttdata.SesionManager.SessionManager
import kotlinx.coroutines.launch

class CancelarReservaViewModel : ViewModel() {

    var isDeleting by mutableStateOf(false)
    var deleteSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    // Función para cancelar PUESTO
    fun confirmarCancelacionPuesto(idReserva: Int?) {
        if (idReserva == null) return
        viewModelScope.launch {
            isDeleting = true
            errorMessage = ""
            val exito = ReservaService.eliminarReservaPuesto(idReserva)
            if (exito) {
                SessionManager.reservasPuestos = SessionManager.reservasPuestos?.filter {
                    it.idReserva != idReserva
                } ?: emptyList()
                deleteSuccess = true
            } else {
                errorMessage = "Error al eliminar puesto en el servidor."
                isDeleting = false
            }
        }
    }

    // Función para cancelar SALA
    fun confirmarCancelacionSala(idReserva: Int?) {
        if (idReserva == null) return
        viewModelScope.launch {
            isDeleting = true
            errorMessage = ""
            val exito = ReservaService.eliminarReservaSala(idReserva)
            if (exito) {
                SessionManager.reservasSalas = SessionManager.reservasSalas?.filter {
                    it.idReserva != idReserva
                } ?: emptyList()
                deleteSuccess = true
            } else {
                errorMessage = "Error al eliminar sala en el servidor."
                isDeleting = false
            }
        }
    }
}