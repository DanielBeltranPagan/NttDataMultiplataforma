package com.example.nttdata.ui.ModificarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.DTOS.ReservaSalaDTO
import com.example.nttdata.ReservaService.ReservaService
import com.example.nttdata.SesionManager.SessionManager

class ModificarReservaViewModel(private val reserva: Any) : ViewModel() {

    // Extraemos solo la parte de la hora (HH:mm) para mostrarla en la UI
    var fecha by mutableStateOf(if (reserva is ReservaPuestoDTO) reserva.fecha ?: "" else (reserva as ReservaSalaDTO).fecha ?: "")
    var hora by mutableStateOf(if (reserva is ReservaPuestoDTO) reserva.horaInicio?.substringAfter("T")?.take(5) ?: "" else (reserva as ReservaSalaDTO).horaInicio?.substringAfter("T")?.take(5) ?: "")

    var isSaving by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun onFechaChanged(newValue: String) { fecha = newValue }
    fun onHoraChanged(newValue: String) { hora = newValue }

    suspend fun confirmarCambios(): Boolean {
        isSaving = true
        errorMessage = ""

        // Preparamos el formato LocalDateTime: "yyyy-MM-ddTHH:mm:00"
        // Java espera la 'T' para separar fecha de hora.
        val horaFormateadaISO = "${fecha}T${hora}:00"

        return if (reserva is ReservaPuestoDTO) {
            val id = reserva.idReserva ?: return false
            // Actualizamos el DTO con el formato que entiende el Backend
            val actualizada = reserva.copy(
                fecha = fecha,
                horaInicio = horaFormateadaISO,
                horaFin = "${fecha}T23:59:59" // Ajusta la hora de fin según necesites
            )

            val exito = ReservaService.modificarReservaPuesto(id, actualizada)
            if (exito) {
                SessionManager.reservasPuestos = SessionManager.reservasPuestos.map { if (it.idReserva == id) actualizada else it }
            } else { errorMessage = "Error al modificar puesto" }
            isSaving = false
            exito
        } else {
            val sala = reserva as ReservaSalaDTO
            val id = sala.idReserva ?: return false
            // Actualizamos el DTO con el formato que entiende el Backend
            val actualizada = sala.copy(
                fecha = fecha,
                horaInicio = horaFormateadaISO,
                horaFin = "${fecha}T23:59:59"
            )

            val exito = ReservaService.modificarReservaSala(id, actualizada)
            if (exito) {
                SessionManager.reservasSalas = SessionManager.reservasSalas.map { if (it.idReserva == id) actualizada else it }
            } else { errorMessage = "Error al modificar sala" }
            isSaving = false
            exito
        }
    }
}