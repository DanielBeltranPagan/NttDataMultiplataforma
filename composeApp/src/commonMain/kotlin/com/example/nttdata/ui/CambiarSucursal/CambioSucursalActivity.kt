package com.example.nttdata.ui.CambiarSucursal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.ui.RealizarReserva.ReservationActivityScreen
import com.example.nttdata.ui.RealizarReserva.ReservationScreen
import com.example.nttdata.ui.RealizarReserva.ReservationViewModel


class CambioSucursalActivity : Screen {
    @Composable
    override fun Content() {
        CambioSucursalScreen()
    }
}

@Composable
fun CambioSucursalScreen(viewModel: CambiarSucursalScreenModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {

            // 1. Mostrar Sucursal Actual (Reactivo al ViewModel)
            Text(
                text = "Sucursal actual",
                color = Color(0xFF0072BB),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Surface(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(
                    text = viewModel.sucursalActualNombre,
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Botón Consultar Estado
            Button(
                onClick = {
                    navigator.push(ReservationActivityScreen(reservationViewModel = ReservationViewModel()))
                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text(text = "Hacer Reserva en esta sucursal", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Dropdown con datos reales del ViewModel
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (viewModel.isLoading) "Cargando..." else "Cambiar sucursal",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f).background(Color.White)
                ) {
                    // Iteramos sobre la lista que viene de la base de datos
                    viewModel.listaSucursales.forEach { sucursal ->
                        DropdownMenuItem(
                            text = { Text(text = sucursal.nombre) },
                            onClick = {
                                viewModel.actualizarSeleccion(sucursal.nombre)
                                expanded = false
                            }
                        )
                    }

                    if (viewModel.listaSucursales.isEmpty() && !viewModel.isLoading) {
                        DropdownMenuItem(
                            text = { Text("No hay sucursales disponibles") },
                            onClick = { expanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 3. Botón Confirmar
            Button(
                onClick = {
                    val sucursalSeleccionada = viewModel.listaSucursales.find { it.nombre == viewModel.sucursalActualNombre }

                    if (sucursalSeleccionada != null && SessionManager.idUsuario != null) {
                        // 1. Lo mandamos a la base de datos (IntelliJ -> Postgres)
                        viewModel.guardarCambioEnServidor(
                            idUsuario = SessionManager.idUsuario!!,
                            idSucursal = sucursalSeleccionada.id_sucursal!!
                        )

                        // 2. Lo guardamos en el móvil para esta sesión
                        SessionManager.nombreSucursal = sucursalSeleccionada.nombre

                        // 3. Volvemos atrás
                        navigator.pop()
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(text = "Confirmar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

