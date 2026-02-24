package com.example.nttdata.ui.GestionarReserva

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.rememberAsyncImagePainter
import com.example.nttdata.DTOS.ReservaPuestoDTO
import com.example.nttdata.ui.CancelarReserva.CancelarReservaActivity
import com.example.nttdata.ui.ModificarReserva.paginaModificarReservaScreen
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.logo


class ReservasActivity(val reservasViewModel: ReservasViewModel= ReservasViewModel()): Screen{
    @Composable
    override fun Content() {
        ReservasScreen()
    }
}

// Data Model
data class Reserva(
    val branch: String,
    val date: String,
    val time: String,
    val workspace: String
)

@Composable
fun ReservasScreen(viewModel: ReservasViewModel= ReservasViewModel()) {
    val reservas = viewModel.listaReservas
    val isLoading = viewModel.isLoading
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        // Title
        Text(
            text = "Tus Reservas:",
            color = Color(0xFF0072BB),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // List Content

        if (isLoading) {
            // Mostramos un círculo de carga si está bajando los datos
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF0072BB))
            }
        } else if (reservas.isEmpty()) {
            // Mensaje por si el usuario no tiene reservas
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes reservas activas", color = Color.Gray)
            }
        } else {
            // Lista real
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(reservas) { reserva ->
                    ReservaItem(reserva)
                }
            }
        }


    }
}

@Composable
fun ReservaItem(reserva: ReservaPuestoDTO) {
    val navigator: Navigator =LocalNavigator.currentOrThrow
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFF0072BB)),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Reservation Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = reserva.idReserva.toString(),
                        color = Color(0xFF0072BB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha: ${reserva.fecha}", fontSize = 14.sp)
                    Text(text = "Hora: ${reserva.horaInicio}", fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(24.dp)) // Approximate check alignment
                    Text(text = "Espacio de trabajo: ${reserva.idPuesto}", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color(0xFF0072BB), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navigator.push(CancelarReservaActivity()) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Cancelar", fontSize = 12.sp)
                }

                Button(
                    onClick = {navigator.push(paginaModificarReservaScreen())},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Modificar", fontSize = 12.sp)
                }
            }
        }
    }
}


@Composable
fun PreviewReservasList() {
    ReservasScreen()
}
