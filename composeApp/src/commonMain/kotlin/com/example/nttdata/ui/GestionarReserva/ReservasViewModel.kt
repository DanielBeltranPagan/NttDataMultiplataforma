package com.example.nttdata.ui.GestionarReserva

import androidx.lifecycle.ViewModel
import com.example.nttdata.SesionManager.SessionManager
class ReservasViewModel : ViewModel() {
    // Ya no hay estado local de 'listaReservas', usamos directamente el SessionManager
    // Al ser mutableStateOf en SessionManager, la UI se actualizará sola
    val reservasPuestos get() = SessionManager.reservasPuestos
    val reservasSalas get() = SessionManager.reservasSalas

    // Eliminamos todo el bloque init e httpClient
}