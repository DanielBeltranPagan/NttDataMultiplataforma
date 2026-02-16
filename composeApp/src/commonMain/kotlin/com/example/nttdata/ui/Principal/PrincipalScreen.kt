package com.example.nttdata

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
// IMPORTS ESPECÍFICOS PARA EVITAR ERRORES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.core.model.rememberScreenModel
import org.example.project.ui.principal.PrincipalScreenModel

class PrincipalScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { PrincipalScreenModel() }
        val state by screenModel.state.collectAsState()

        // Estado para controlar si mostramos un aviso de cerrar sesión
        var showLogoutDialog by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("NTT DATA", color = Color.White, fontWeight = FontWeight.Bold)
                    },
                    // AQUÍ AÑADIMOS EL BOTÓN DE LA PERSONITA
                    actions = {
                        IconButton(onClick = { showLogoutDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Perfil",
                                tint = Color.White // Para que resalte en el azul
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF0072BB)
                    )
                )
            },
            bottomBar = {
                if (navigator.canPop) {
                    BottomAppBar(containerColor = Color(0xFFF5F5F5)) {
                        TextButton(onClick = { navigator.pop() }) {
                            Text("< VOLVER", color = Color(0xFF0072BB), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        ) { paddingValues ->

            // Diálogo de confirmación para cerrar sesión
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Cerrar Sesión") },
                    text = { Text("¿Estás seguro de que deseas salir?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            // Aquí pondrías la lógica para volver al Login
                            // navigator.replaceAll(LoginScreen())
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

            Surface(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (state.isLoading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF0072BB))
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.sucursalActual, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = state.mensajeBienvenida, color = Color.Gray, modifier = Modifier.padding(bottom = 30.dp))

                        PrincipalMenuButton("Reservar Espacio") { }
                        PrincipalMenuButton("Gestionar Reservas") { }
                        PrincipalMenuButton("Cambiar Sucursal") { }
                    }
                }
            }
        }
    }
}

@Composable
fun PrincipalMenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(bottom = 16.dp, start = 24.dp, end = 24.dp).fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB))
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}