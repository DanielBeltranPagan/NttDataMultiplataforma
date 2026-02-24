package com.example.nttdata.ui.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.ui.Principal.Pagina2
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

class paginaIniciarSesionScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        LoginScreen(navigator = navigator)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(
        navigator: Navigator,
        // Usamos el ViewModel que ya tiene la lógica de Ktor y SessionManager
        viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    ) {
        // Manejo de navegación tras login exitoso
        LaunchedEffect(viewModel.loginExitoso) {
            if (viewModel.loginExitoso) {
                // Usamos replaceAll para que el usuario no pueda volver atrás al login con el botón del móvil
                navigator.replaceAll(Pagina2())
                viewModel.navegacionCompletada()
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Image(
                            painter = painterResource(Res.drawable.logo),
                            contentDescription = "NTT DATA Logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.height(75.dp),
                            colorFilter = tint(Color.White)
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF0072BB)
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bienvenido",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 32.dp),
                        color = Color(0xFF333333)
                    )

                    // Campo ID Usuario (ahora vinculado a la lógica de Int en el ViewModel)
                    OutlinedTextField(
                        value = viewModel.username,
                        onValueChange = { viewModel.username = it },
                        label = { Text("Email de usuario") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !viewModel.isLoading // Bloquear edición mientras carga
                    )

                    // Campo Contraseña
                    OutlinedTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !viewModel.isLoading // Bloquear edición mientras carga
                    )

                    // Mensaje de Error dinámico (viene de la respuesta del servidor)
                    if (viewModel.errorMessage.isNotEmpty()) {
                        Text(
                            text = viewModel.errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón Iniciar Sesión con estado de carga
                    Button(
                        onClick = { viewModel.iniciarSesion() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0072BB),
                            disabledContainerColor = Color(0xFF80B9DD) // Color más claro si está cargando
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(55.dp),
                        enabled = !viewModel.isLoading // Deshabilitar click repetido
                    ) {
                        if (viewModel.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Iniciar sesión",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}