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
import cafe.adriel.voyager.navigator.currentOrThrow

import org.jetbrains.compose.resources.painterResource


import coil3.compose.rememberAsyncImagePainter


import com.example.nttdata.ui.GestionarReserva.Reserva
import com.example.nttdata.ui.GestionarReserva.ReservaItem

class CancelarReservaActivity : Screen {
    @Composable
    override fun Content() {
        CancelarReservaScreen()
    }
}

@Composable
fun CancelarReservaScreen() {
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

            ReservaItem(sampleReserva)

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

