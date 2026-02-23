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
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.rememberAsyncImagePainter
import com.example.nttdata.ui.Principal.Pagina2

class paginaIniciarSesionScreen : Screen {

    @Composable
    override fun Content() {
        // Obtenemos el navigator de Voyager correctamente
        val navigator = LocalNavigator.currentOrThrow

        // Se lo pasamos a la función LoginScreen
        LoginScreen(navigator = navigator)
    }

    @Composable
    fun LoginScreen(navigator: Navigator) {
        // Estados de los campos
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Usamos una Box para contener todo
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Decoración superior (barra azul)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0xFF0072BB))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = rememberAsyncImagePainter(
                        "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/81XQccjZHz/6jwa1p2b_expires_30_days.png"
                    ),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )

                // Campo Usuario
                Text(
                    text = "Usuario",
                    color = Color(0xFF0072BB),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    singleLine = true,
                    placeholder = { Text("Introduce tu usuario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                // Campo Contraseña
                Text(
                    text = "Contraseña",
                    color = Color(0xFF0072BB),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    placeholder = { Text("Introduce tu contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                // Texto "Olvidé la contraseña"
                Text(
                    text = "Olvidé la contraseña",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 32.dp)
                )

                // Botón Iniciar Sesión
                Button(
                    onClick = {
                        // Por ahora solo navegamos, luego pondremos la lógica del API
                        navigator.push(Pagina2())
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(57.dp)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}