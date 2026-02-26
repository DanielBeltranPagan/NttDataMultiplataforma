package com.example.nttdata.ui.RealizarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.ReservaService.ReservaService
import com.example.nttdata.SesionManager.SessionManager
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {
    var sucursal by mutableStateOf("Castellon de la plana (UJI)")
    var fecha by mutableStateOf("")
    var horaInicio by mutableStateOf("")
    var horaFinal by mutableStateOf("")

    fun onSucursalChanged(newValue: String) { sucursal = newValue }
    fun onFechaChanged(newValue: String) { fecha = newValue }
    fun onHoraInicioChanged(newValue: String) { horaInicio = newValue }
    fun onHoraChangedFinal(newValue: String) { horaFinal = newValue }
    fun confirmarReservaFinal(idAsiento: String,onExito: () -> Unit) {
        viewModelScope.launch {
            val nuevaReserva = ReservaPuestoDTO(
                idPuesto = idAsiento.toInt(),
                idUsuario = SessionManager.idUsuario,
                fecha = fecha,
                horaInicio = if (horaInicio.length == 5) "$horaInicio:00" else horaInicio,
                horaFin = if (horaFinal.length == 5) "$horaFinal:00" else horaFinal
            )
            val exito = ReservaService.crearReservaPuesto(nuevaReserva)
            if (exito) {
                onExito()
            }

        }
    }

}