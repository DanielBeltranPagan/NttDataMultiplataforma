package com.example.nttdata // Asegúrate de que coincida con tu carpeta

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
    val navigator = LocalNavigator.currentOrThrow
    MaterialTheme {
        // Navigator gestiona qué pantalla se ve
        Navigator(screen = paginaIniciarSesionScreen()) { navigator ->
            var showLogoutDialog by remember { mutableStateOf(false) }

            // COMPROBACIÓN: ¿Estamos en la pantalla de login?
            val isLoginScreen = navigator.lastItem is paginaIniciarSesionScreen

            Scaffold(
                topBar = {
                    // Solo se muestra si NO estamos en Login
                    if (!isLoginScreen) {
                        CenterAlignedTopAppBar(
                            title = {
                                Image(
                                    painter = painterResource(Res.drawable.logo),
                                    contentDescription = "NTT DATA Logo",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxHeight(0.9f)
                                        .width(100.dp) // Valor más realista que 10000
                                        .wrapContentWidth(),
                                    colorFilter = tint(Color.White)
                                )
                            },
                            actions = {
                                IconButton(onClick = { showLogoutDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Perfil",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0072BB)
                            )
                        )
                    }
                },
                bottomBar = {
                    // Solo se muestra si NO estamos en Login
                    if (!isLoginScreen) {
                        MiBottomBarNavegacion(navigator)
                    }
                }
            ) { paddingValues ->
                // Si es login, usamos Modifier.fillMaxSize() para que ocupe toda la pantalla
                // Si no es login, aplicamos el padding para que las barras no tapen el contenido
                val contentModifier = if (isLoginScreen) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.fillMaxSize().padding(paddingValues)
                }

                Box(modifier = contentModifier) {
                    CurrentScreen()
                }
            }

            // Lógica global de Cerrar Sesión
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Cerrar Sesión") },
                    text = { Text("¿Estás seguro de que deseas salir?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            navigator.popAll()
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
        }){
            Icon(
                Icons.Default.DateRange,
                "Reservar",
                tint = Color.White,
                modifier = Modifier.size(30.dp))
        }
        IconButton(onClick = {
            navigator.push(ReservationActivityScreen())
        }){
            Icon(Icons.Default.DateRange,
                "Gestionar",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(30.dp))

        }
        IconButton(onClick = {
            navigator.push(CambioSucursalActivity())
        }){
            Icon(
                Icons.Default.Home,
                "Sucursales",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(30.dp))

        }

   }
}