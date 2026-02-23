package com.example.nttdata.ui.RealizarReserva

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import org.jetbrains.compose.resources.painterResource
//import nttdata_app.composeapp.generated.resources.Res
//import nttdata_app.composeapp.generated.resources.logo
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.AsyncImage
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.ui.ModificarReserva.ModificarField
import com.example.nttdata.ui.SeleccionarSitio.SeleccionAsientoActivity


class ReservationActivityScreen: Screen{
    @Composable
    override fun Content() {
        ReservationScreen()
    }
}

@Composable
fun ReservationScreen() {
    val scrollState = rememberScrollState()
    val navigator: Navigator = LocalNavigator.currentOrThrow

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars) // Handle edge-to-edge padding
    ) {

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Text(
                text = "Introduce datos para la reserva:",
                color = Color(0xFF0072BB),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Form Fields
            ModificarField(label = "Sucursal", value = "Castellon de la plana (UJI)")
            ModificarField(label = "Fecha", value = "19/12/2025")
            ModificarField(label = "Hora:", value = "10:00-13:00")
            ModificarField(label = "Espacio de Trabajo Preferido:", value = "5b")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navigator.push(SeleccionAsientoActivity()) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Seleccionar sitio",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }


    }

    @Composable
    fun ReservationField(label: String, value: String) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = label,
                color = Color(0xFF0072BB),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE3F2FD),
                    unfocusedContainerColor = Color(0xFFE3F2FD),
                    disabledContainerColor = Color(0xFFE3F2FD),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}
@Composable
fun PreviewReservationScreen() {
    ReservationScreen()
}
