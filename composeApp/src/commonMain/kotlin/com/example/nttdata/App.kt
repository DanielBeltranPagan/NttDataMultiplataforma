package com.example.nttdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.ui.CambiarSucursal.CambioSucursalActivity
import com.example.nttdata.ui.GestionarReserva.ReservasActivity
import com.example.nttdata.ui.Login.LoginScreen
import com.example.nttdata.ui.Login.paginaIniciarSesionScreen
import com.example.nttdata.ui.RealizarReserva.ReservationActivityScreen
import com.example.nttdata.ui.RealizarReserva.ReservationScreen
import com.example.nttdata.ui.SeleccionarSitio.SeleccionAsientoActivity
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // SE ELIMINÓ LA LÍNEA: val navigator = LocalNavigator.currentOrThrow (Causaba el error)

    MaterialTheme {
        // Navigator gestiona qué pantalla se ve y nos entrega la variable 'navigator' funcional
        Navigator(screen = paginaIniciarSesionScreen()) { navigator ->
            var showLogoutDialog by remember { mutableStateOf(false) }

            // Comprobamos si la pantalla actual es la de login
            val isLoginScreen = navigator.lastItem is paginaIniciarSesionScreen

            Scaffold(
                topBar = {
                    if (!isLoginScreen) {
                        CenterAlignedTopAppBar(
                            title = {
                                // AJUSTE AQUÍ: Altura fija para que no crezca infinito
                                Image(
                                    painter = painterResource(Res.drawable.logo),
                                    contentDescription = "NTT DATA Logo",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.height(60.dp), // Altura razonable
                                    colorFilter = tint(Color.White)
                                )
                            },
                            actions = {
                                IconButton(onClick = { showLogoutDialog = true }) {
                                    Icon(Icons.Default.AccountCircle, "Perfil", tint = Color.White)
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0072BB)
                            )
                        )
                    }
                },
                bottomBar = {
                    if (!isLoginScreen) {
                        MiBottomBarNavegacion(navigator)
                    }
                }
            ) { paddingValues ->
                // IMPORTANTE: Aplicar paddingValues para que el contenido
                // baje y no sea tapado por la TopBar azul
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                    CurrentScreen()
                }
            }

            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Cerrar Sesión") },
                    text = { Text("¿Estás seguro de que deseas salir?") },
                    confirmButton = {
                        // Cambiado popAll por replaceAll para evitar pantalla en blanco
                        TextButton(onClick = {
                            showLogoutDialog = false
                            navigator.replaceAll(paginaIniciarSesionScreen())
                        }) {
                            Text("Sí, salir", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MiBottomBarNavegacion(navigator: Navigator) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF0072BB)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navigator.push(ReservasActivity())
        }) {
            Icon(
                Icons.Default.DateRange,
                "Reservar",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(onClick = {
            navigator.push(ReservationActivityScreen())
        }) {
            Icon(
                Icons.Default.Edit, // Cambiado icono para diferenciarlo del anterior
                "Gestionar",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(onClick = {
            navigator.push(CambioSucursalActivity())
        }) {
            Icon(
                Icons.Default.Home,
                "Sucursales",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}