package com.example.nttdata.SesionManager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.DTOS.ReservaSalaDTO

object SessionManager {
        // Usamos mutableStateOf para que si cambia aquí,
        // todas las pantallas que lo lean se actualicen solas
    var idUsuario by mutableStateOf<Int?>(null)
    var correo by mutableStateOf("")
    var nombreSucursal by mutableStateOf("Seleccione sucursal")
    var rango by mutableStateOf("USER")

    var reservasPuestos by mutableStateOf<List<ReservaPuestoDTO>>(emptyList())

    var reservasSalas by mutableStateOf<List<ReservaSalaDTO>>(emptyList())

    val estaLogueado: Boolean get() = idUsuario != null

fun cerrarSesion() {
   idUsuario = null
   correo = ""
   nombreSucursal = "Seleccione sucursal"
    rango = "USER"
    reservasPuestos = emptyList()
    reservasSalas = emptyList()
}
}