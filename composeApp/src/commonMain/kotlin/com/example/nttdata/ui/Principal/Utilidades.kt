package com.example.nttdata.ui.Principal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class Pagina2 : Screen {
    @Composable
    override fun Content() {
        PantallaUtilidades()
    }
}

@Composable
fun PantallaUtilidades() {
    // Obtenemos el navegador de Voyager
    val navigator = LocalNavigator.currentOrThrow

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 50.dp), // Ajustado de 193dp que era mucho
        ) {

            val buttonModifier = Modifier
                .padding(bottom = 32.dp, start = 23.dp, end = 23.dp)
                .fillMaxWidth()
                .height(60.dp) // Altura fija para que se vea mejor

            // Botón 1: Reservar
            Button(
                onClick = {
                    // navigator.push(ReservationScreen()) // Cambia por tu clase Screen real
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(15.dp),
                modifier = buttonModifier
            ) {
                Text("Reservar Espacio de trabajo", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // Botón 2: Gestionar
            Button(
                onClick = {
                    // navigator.push(GestionarScreen())
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(15.dp),
                modifier = buttonModifier
            ) {
                Text("Gestionar reservas", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // Botón 3: Sucursal
            Button(
                onClick = { /* navigator.push(SucursalScreen()) */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(15.dp),
                modifier = buttonModifier
            ) {
                Text("Cambiar Sucursal", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}