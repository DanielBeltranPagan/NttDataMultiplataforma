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
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.DTOS.ReservaSalaDTO

class CancelarReservaActivity(val reserva: Any) : Screen {
    @Composable
    override fun Content() {
        CancelarReservaScreen(reserva)
    }
}

@Composable
fun CancelarReservaScreen(reserva: Any) {
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

            CancelarReservaItem(reserva, navigator)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CancelarReservaItem(
    reserva: Any,
    navigator: Navigator,
    viewModel: CancelarReservaViewModel = viewModel()
) {
    var showReservaDialog by remember { mutableStateOf(false) }

    // Extraemos datos según el tipo de objeto
    val idReserva = if (reserva is ReservaPuestoDTO) reserva.idReserva else (reserva as ReservaSalaDTO).idReserva
    val fecha = if (reserva is ReservaPuestoDTO) reserva.fecha else (reserva as ReservaSalaDTO).fecha
    val hora = if (reserva is ReservaPuestoDTO) reserva.horaInicio else (reserva as ReservaSalaDTO).horaInicio
    val etiquetaPosicion = if (reserva is ReservaPuestoDTO) "Puesto: ${reserva.idPuesto}" else "Sala: ${(reserva as ReservaSalaDTO).idSala}"

    LaunchedEffect(viewModel.deleteSuccess) {
        if (viewModel.deleteSuccess) {
            navigator.pop()
        }
    }

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
                        text = "Reserva #${idReserva ?: 0}",
                        color = Color(0xFF0072BB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha: ${fecha ?: "---"}", fontSize = 14.sp)
                    Text(text = "Hora: ${hora?.take(5) ?: "--:--"}", fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = etiquetaPosicion, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF0072BB), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { navigator.pop() },
                    enabled = !viewModel.isDeleting,
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(1.dp, Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "No cancelar", color = Color(0xFF0072BB))
                }

                Button(
                    onClick = { showReservaDialog = true },
                    enabled = !viewModel.isDeleting,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Cancelar reserva")
                }
            }

            if (showReservaDialog) {
                AlertDialog(
                    onDismissRequest = { if (!viewModel.isDeleting) showReservaDialog = false },
                    title = { Text(text = "Confirmar acción") },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (viewModel.isDeleting) {
                                CircularProgressIndicator(color = Color(0xFF0072BB))
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Procesando baja...")
                            } else {
                                Text(text = "¿Estás seguro de que quieres eliminar esta reserva permanentemente?")
                                if (viewModel.errorMessage.isNotEmpty()) {
                                    Text(text = viewModel.errorMessage, color = Color.Red, fontSize = 12.sp)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            enabled = !viewModel.isDeleting,
                            onClick = {
                                if (reserva is ReservaPuestoDTO) {
                                    viewModel.confirmarCancelacionPuesto(reserva.idReserva)
                                } else if (reserva is ReservaSalaDTO) {
                                    viewModel.confirmarCancelacionSala(reserva.idReserva)
                                }
                            }
                        ) {
                            Text("Confirmar", color = if (viewModel.isDeleting) Color.Gray else Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            enabled = !viewModel.isDeleting,
                            onClick = { showReservaDialog = false }
                        ) {
                            Text("Atrás")
                        }
                    }
                )
            }
        }
    }
}