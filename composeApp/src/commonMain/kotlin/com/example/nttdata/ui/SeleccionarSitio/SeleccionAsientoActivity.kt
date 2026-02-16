package com.example.nttdata.ui.SeleccionarSitio

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. NAVEGACIÓN VOYAGER
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

// 2. RECURSOS MULTIPLATAFORMA (Sustituye a ui.res.painterResource)
import org.jetbrains.compose.resources.painterResource
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.logo

// 3. COIL 3 (Multiplatform)
import coil3.compose.rememberAsyncImagePainter
import nttdata.composeapp.generated.resources.oficina

class SeleccionAsientoActivity : Screen{
    @Composable
    override fun Content() {
        SeleccionAsientoScreen()
    }
}

data class Asiento(
    val id: String,
    val xPercent: Float,
    val yPercent: Float,
    val isAvailable: Boolean,
    val isMeetingRoom: Boolean = false,
    val favorito: Boolean = false
)

@Composable
fun SeleccionAsientoScreen() {
    var selectedAsientoId by remember { mutableStateOf<String?>(null) }

    // Mock data - Reusing coordinates verified in EstadoSucursalActivity
    val topCluster = listOf(
        Asiento("1", 0.93f, 0.13f, true), Asiento("2", 0.93f, 0.33f, true),
        Asiento("3", 0.93f, 0.53f, true), Asiento("4", 0.93f, 0.73f, true, favorito = true),
        Asiento("5", 0.79f, 0.13f, true), Asiento("6", 0.79f, 0.33f, true),
        Asiento("7", 0.79f, 0.53f, true), Asiento("8", 0.79f, 0.73f, true),
    )

    val bottomCluster = listOf(
        Asiento("9", 0.22f, 0.10f, true),
        Asiento("10", 0.22f, 0.30f, true, favorito = true),
        Asiento("11", 0.22f, 0.50f, true),
        Asiento("12", 0.22f, 0.70f, true),
        Asiento("13", 0.22f, 0.90f, true),
        Asiento("14", 0.07f, 0.10f, true),
        Asiento("15", 0.07f, 0.30f, true),
        Asiento("16", 0.07f, 0.50f, true),
        Asiento("17", 0.07f, 0.70f, true, favorito = true),
        Asiento("18", 0.07f, 0.90f, true)
    )

    val meetingRoom = listOf(Asiento("M1", 0.50f, 0.23f, false, isMeetingRoom = true))
    val allAsientos = topCluster + bottomCluster + meetingRoom

    LaunchedEffect(Unit) {
        selectedAsientoId = "8"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        // --- MAP CONTENT ---
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
                    .rotate(270f) // Rotate parent container like in EstadoSucursalActivity
                    .border(2.dp, Color.Black)
            ) {
                // Plano rotado con el contenedor
                Image(
                    painter = painterResource(Res.drawable.oficina),
                    contentDescription = "Plano Oficina",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay de Asientos
                allAsientos.forEach { asiento ->
                    val isSelected = asiento.id == selectedAsientoId
                    val color = when {
                        isSelected -> Color(0xFF0072BB) // Blue for selected
                        asiento.favorito -> Color(0xFF0072BB) // Blue for favorite
                        asiento.isAvailable -> Color.Green // Standard Green
                        else -> Color.Red // Red for occupied/unavailable
                    }

                    val size =
                        if (asiento.isMeetingRoom) 50.dp else 28.dp // Restored verified sizes

                    Box(
                        modifier = Modifier
                            .offset(
                                x = (mapSize * asiento.xPercent) - (size / 2),
                                y = (mapSize * asiento.yPercent) - (size / 2)
                            )
                            .size(size)
                            .clip(CircleShape)
                            .background(color)
                            .border(2.dp, Color.Black, CircleShape) // Restored 2.dp border
                            .clickable {
                                if (asiento.isAvailable) {
                                    selectedAsientoId = asiento.id
                                }
                            }
                    )
                }
            }
        }

        // --- CONFIRM BUTTON ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = { /* Acción de confirmar */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "Confirmar Reserva",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}