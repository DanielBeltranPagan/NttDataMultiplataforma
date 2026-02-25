package com.example.nttdata.ui.ModificarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.ReservaService.ReservaService
import kotlinx.coroutines.launch

class ModificarReservaViewModel(private val reserva: ReservaPuestoDTO) : ViewModel() {
    var fecha by mutableStateOf(reserva.fecha)
    var hora by mutableStateOf(reserva.horaInicio)


    fun onFechaChanged(newValue: String) { fecha = newValue }
    fun onHoraChanged(newValue: String) { hora = newValue }

    fun confirmarCambios(){
        val id = reserva.idReserva ?: return
       
        viewModelScope.launch {
            try {
                val reservaActualizada=reserva.copy(
                    fecha=fecha,
                    horaInicio = hora
                )
                val response= ReservaService.modificarReservaPuesto(id,reservaActualizada)

            }catch (e : Exception){
                e.printStackTrace()
            }
        }

    }


}