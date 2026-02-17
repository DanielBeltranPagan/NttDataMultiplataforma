package com.example.nttdata.ui.CancelarReserva

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

import org.jetbrains.compose.resources.painterResource


import coil3.compose.rememberAsyncImagePainter


import com.example.nttdata.ui.GestionarReserva.Reserva
import com.example.nttdata.ui.GestionarReserva.ReservaItem
import com.example.nttdata.ui.GestionarReserva.ReservasActivity
import kotlinx.coroutines.launch

class CancelarReservaActivity : Screen {
    @Composable
    override fun Content() {
        CancelarReservaScreen()
    }
}

@Composable
fun CancelarReservaScreen() {
    val navigator:Navigator=LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {


        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Confirmation Box
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFF0072BB)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Seguro que quiere cancelar la siguiente Reserva?",
                        color = Color(0xFF0072BB),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reservation Details
            // Using a local data structure or reusing from ReservasActivity if possible.
            // For this view, we'll hardcode the sample data shown in image.
            val sampleReserva = Reserva(
                branch = "Sucursal Castellon:",
                date = "19/12/25",
                time = "10:00-13:00",
                workspace = "5b"
            )

            CancelarReservaItem(sampleReserva,navigator)

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Handle No Cancel */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(55.dp)
                ) {
                    Text(text = "No cancelar reserva", fontSize = 14.sp)
                }

                Button(
                    onClick = { /* Handle Cancel */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .height(55.dp)
                ) {
                    Text(text = "Cancelar reserva", fontSize = 14.sp)
                }
            }
        }
    }
}
@Composable
fun CancelarReservaItem(reserva: Reserva, navigator: Navigator) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                        text = reserva.branch,
                        color = Color(0xFF0072BB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha: ${reserva.date}", fontSize = 14.sp)
                    Text(text = "Hora: ${reserva.time}", fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(24.dp)) // Approximate check alignment
                    Text(text = "Espacio de trabajo: ${reserva.workspace}", fontSize = 14.sp)
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
                    onClick = { navigator.push(ReservasActivity()) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "No cancelar", fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Reserva cancelada")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Cancelar reserva", fontSize = 12.sp)
                }
            }
        }
    }
}
