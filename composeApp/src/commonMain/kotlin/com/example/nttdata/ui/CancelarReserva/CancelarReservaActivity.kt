package com.example.nttdata.ui.CancelarReserva

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.ui.GestionarReserva.ReservasActivity

// Pasamos la reserva como parámetro para saber qué estamos cancelando
class CancelarReservaActivity(val reserva: ReservaPuestoDTO) : Screen {
    @Composable
    override fun Content() {
        CancelarReservaScreen(reserva)
    }
}

@Composable
fun CancelarReservaScreen(reserva: ReservaPuestoDTO) {
    val navigator = LocalNavigator.currentOrThrow

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¿Confirmar cancelación?",
                color = Color(0xFF0072BB),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Llamamos al item con los datos reales
            CancelarReservaItem(reserva, navigator)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CancelarReservaItem(reserva: ReservaPuestoDTO, navigator: Navigator) {
    var showReservaDialog by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFF0072BB)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Reserva #${reserva.idReserva}",
                        color = Color(0xFF0072BB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha: ${reserva.fecha}", fontSize = 14.sp)
                    Text(text = "Hora: ${reserva.horaInicio.take(5)}", fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Puesto: ${reserva.idPuesto}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF0072BB), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón Volver
                OutlinedButton(
                    onClick = { navigator.pop() }, // Simplemente vuelve atrás
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(1.dp, Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "No cancelar", color = Color(0xFF0072BB))
                }

                // Botón Confirmar
                Button(
                    onClick = { showReservaDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Cancelar reserva")
                }
            }

            if (showReservaDialog) {
                AlertDialog(
                    onDismissRequest = { showReservaDialog = false },
                    title = { Text(text = "Confirmar acción") },
                    text = { Text(text = "¿Estás seguro de que quieres eliminar esta reserva permanentemente?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showReservaDialog = false
                            // Aquí iría la llamada a la API DELETE en el futuro
                            navigator.push(ReservasActivity())
                        }) {
                            Text("Confirmar", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showReservaDialog = false }) {
                            Text("Atrás")
                        }
                    }
                )
            }
        }
    }
}