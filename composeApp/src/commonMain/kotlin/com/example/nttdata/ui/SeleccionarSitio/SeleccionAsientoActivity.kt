package com.example.nttdata.ui.SeleccionarSitio

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.oficina
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.ReservaService.ReservaService
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.ui.GestionarReserva.ReservasActivity
import kotlinx.coroutines.launch

class SeleccionAsientoActivity(val fecha: String, val horaInicio: String, val horaFinal: String) : Screen {
    @Composable
    override fun Content() {
        SeleccionAsientoScreen(fecha, horaInicio, horaFinal)
    }
}

data class Asiento(
    val id: String,
    val xPercent: Float,
    val yPercent: Float,
    val isAvailable: Boolean,
    val isMeetingRoom: Boolean = false,
)

@Composable
fun SeleccionAsientoScreen(fecha: String, horaInicio: String, horaFinal: String) {
    var selectedAsientoId by remember { mutableStateOf<String?>(null) }
    var showReservaDialog by remember { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    val allAsientos = listOf(
        Asiento("1", 0.93f, 0.13f, true), Asiento("2", 0.93f, 0.33f, true),
        Asiento("3", 0.93f, 0.53f, true), Asiento("4", 0.93f, 0.73f, true),
        Asiento("5", 0.79f, 0.13f, true), Asiento("6", 0.79f, 0.33f, true),
        Asiento("7", 0.79f, 0.53f, true), Asiento("8", 0.79f, 0.73f, true),
        Asiento("9", 0.22f, 0.10f, true), Asiento("10", 0.22f, 0.30f, true),
        Asiento("11", 0.22f, 0.50f, true), Asiento("12", 0.22f, 0.70f, true),
        Asiento("13", 0.22f, 0.90f, true), Asiento("14", 0.07f, 0.10f, true),
        Asiento("15", 0.07f, 0.30f, true), Asiento("16", 0.07f, 0.50f, true),
        Asiento("17", 0.07f, 0.70f, true), Asiento("18", 0.07f, 0.90f, true),
        Asiento("M1", 0.50f, 0.23f, false, isMeetingRoom = true)
    )

    LaunchedEffect(Unit) {
        selectedAsientoId = "8"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            val mapSize = 600.dp
            Box(
                modifier = Modifier
                    .size(mapSize)
                    .rotate(270f)
                    .border(2.dp, Color.Black)
            ) {
                Image(
                    painter = painterResource(Res.drawable.oficina),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                allAsientos.forEach { asiento ->
                    val isSelected = asiento.id == selectedAsientoId
                    val color = when {
                        isSelected -> Color(0xFF0072BB)
                        asiento.isAvailable -> Color.Green
                        else -> Color.Red
                    }
                    val size = if (asiento.isMeetingRoom) 50.dp else 28.dp

                    Box(
                        modifier = Modifier
                            .offset(
                                x = (mapSize * asiento.xPercent) - (size / 2),
                                y = (mapSize * asiento.yPercent) - (size / 2)
                            )
                            .size(size)
                            .clip(CircleShape)
                            .background(color)
                            .border(2.dp, Color.Black, CircleShape)
                            .clickable { if (asiento.isAvailable) selectedAsientoId = asiento.id }
                    )
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
            Button(
                onClick = { if (selectedAsientoId != null) showReservaDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp).shadow(4.dp, RoundedCornerShape(10.dp))
            ) {
                Text("Confirmar Reserva", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        if (showReservaDialog) {
            AlertDialog(
                onDismissRequest = { showReservaDialog = false },
                title = { Text("Confirmación", style = MaterialTheme.typography.headlineSmall) },
                text = { Text("¿Confirmar reserva del asiento $selectedAsientoId para el $fecha?") },
                confirmButton = {
                    TextButton(onClick = {

                        // 1. Limpiamos y formateamos las horas con segundos
                        val horaInicioSegundos = if (horaInicio.length == 5) "$horaInicio:00" else horaInicio
                        val horaFinSegundos = if (horaFinal.length == 5) "$horaFinal:00" else horaFinal

                        // 2. Usamos el ID del asiento seleccionado y el usuario de la sesión
                        val reserva = ReservaPuestoDTO(
                            fecha = fecha.trim(),
                            horaInicio = horaInicioSegundos,
                            horaFin = horaFinSegundos,
                            idPuesto = selectedAsientoId?.toIntOrNull() ?: 0, // Usar el real
                            idUsuario = SessionManager.idUsuario ?: 0        // Usar el real
                        )
                        println("DATOS RESERVA: fecha='${reserva.fecha}' horaInicio='${reserva.horaInicio}' horaFin='${reserva.horaFin}' idPuesto=${reserva.idPuesto} idUsuario=${reserva.idUsuario}")

                        scope.launch {
                            val exito = ReservaService.crearReservaPuesto(reserva)
                            if (exito) {
                                showReservaDialog = false
                                // Aquí podrías usar navigator.popUntilRoot() o ir a ReservasActivity
                            }
                        }
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showReservaDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}