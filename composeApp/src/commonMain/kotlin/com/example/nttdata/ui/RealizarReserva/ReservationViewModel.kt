package com.example.nttdata.ui.RealizarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReservationViewModel : ViewModel() {
    var sucursal by mutableStateOf("Castellon de la plana (UJI)")
    var fecha by mutableStateOf("")
    var hora by mutableStateOf("")

    fun onSucursalChanged(newValue: String) { sucursal = newValue }
    fun onFechaChanged(newValue: String) { fecha = newValue }
    fun onHoraChanged(newValue: String) { hora = newValue }

}