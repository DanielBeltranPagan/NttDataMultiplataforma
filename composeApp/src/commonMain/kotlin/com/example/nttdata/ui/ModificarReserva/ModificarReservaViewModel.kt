package com.example.nttdata.ui.ModificarReserva

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

class ModificarReservaViewModel(private val reservaId: Int) {
    var fecha by mutableStateOf("")
    var hora by mutableStateOf("")

    fun onFechaChanged(newValue: String) { fecha = newValue }
    fun onHoraChanged(newValue: String) { hora = newValue }


}