package com.example.nttdata.ui.GestionarReserva

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.DTOS.ReservaSalaDTO
import com.example.nttdata.ui.CancelarReserva.CancelarReservaActivity
import com.example.nttdata.ui.ModificarReserva.paginaModificarReservaScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

class ReservasActivity : Screen {
    @Composable
    override fun Content() {
        ReservasScreen()
    }
}

@Composable
fun ReservasScreen(viewModel: ReservasViewModel = viewModel()) {
    // Estas listas vienen del SessionManager a través del ViewModel
    val puestos = viewModel.reservasPuestos
    val salas = viewModel.reservasSalas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Título
        Text(
            text = "Tus Reservas:",
            color = Color(0xFF0072BB),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        if (puestos.isEmpty() && salas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes reservas activas", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Seccion de Puestos
                items(puestos) { reserva ->
                    ReservaItem(reserva)
                }

                // Seccion de Salas
                items(salas) { sala ->
                    ReservaSalaItem(sala)
                }
            }
        }
    }
}

@Composable
fun ReservaItem(reserva: ReservaPuestoDTO) {
    val navigator = LocalNavigator.currentOrThrow

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFF0072BB)),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "ID: ${reserva.idReserva}",
                        color = Color(0xFF0072BB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha: ${reserva.fecha}", fontSize = 14.sp)
                    Text(
                        text = "Hora: ${reserva.horaInicio?.take(5) ?: "00:00"}",
                        fontSize = 14.sp
                    )                }
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Puesto: ${reserva.idPuesto}", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF0072BB), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navigator.push(CancelarReservaActivity(reserva)) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Cancelar", fontSize = 12.sp)
                }

                Button(
                    onClick = { navigator.push(paginaModificarReservaScreen(reserva)) }, // <-- Pasamos 'reserva'
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    // ... rest
                ) {
                    Text("Modificar")
                }
            }
        }
    }
}

@Composable
fun ReservaSalaItem(sala: ReservaSalaDTO) {
    val navigator = LocalNavigator.currentOrThrow

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        // Podrías cambiar el color del borde a un tono distinto si quisieras diferenciarlo
        border = BorderStroke(1.dp, Color(0xFF0072BB)),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "SALA - ID: ${sala.idReserva ?: 0}",
                        color = Color(0xFF0072BB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha: ${sala.fecha ?: "Sin fecha"}", fontSize = 14.sp)
                    Text(
                        // Usamos substringBefore para quitar cualquier T o espacio,
                        // y take(5) para quedarnos solo con HH:mm
                        text = "Hora: ${sala.horaInicio?.substringAfter("T")?.take(5) ?: "00:00"}-${
                            sala.horaFin?.substringAfter(
                                "T"
                            )?.take(5) ?: "00:00"
                        }",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(24.dp))
                    // Aquí usamos idSala en lugar de idPuesto
                    Text(
                        text = "Sala numero: ${sala.idSala ?: "N/A"}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF0072BB), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navigator.push(CancelarReservaActivity(sala)) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Cancelar", fontSize = 12.sp)
                }

                Button(
                    onClick = { navigator.push(paginaModificarReservaScreen(sala)) }, // Cambiado para pasar 'sala'
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Modificar", fontSize = 12.sp)
                }
            }
        }
    }
}