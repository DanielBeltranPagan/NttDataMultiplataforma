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
import cafe.adriel.voyager.navigator.Navigator
import coil3.compose.rememberAsyncImagePainter // Nota: KMP usa Coil 3
import com.example.nttdata.ui.Principal.Pagina2


class paginaIniciarSesionScreen (
    // val viewmodel : LoginViewModel
): Screen{
        @Composable
        override fun Content() {
            LoginScreen()
        }




@Composable
fun LoginScreen(navigator: Navigator) {
    // Estados de los campos
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context=LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF0072BB))
        )
        Image(
            painter = rememberAsyncImagePainter(
                "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/81XQccjZHz/6jwa1p2b_expires_30_days.png"
            ),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(bottom = 48.dp)
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
            onClick = { navigator.push(Pagina2())},
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

fun inciarSesion(context: Context) {

    // 1. Datos de conexión
    val host = "nttdata.c0vvcxvxqoto.us-east-1.rds.amazonaws.com"
    val port = 5432
    val database = "nttdata"
    val dbUser = "nttdata"
    val dbPassword = "TU_PASSWORD_AQUI" // reemplaza con tu contraseña

    val url = "jdbc:postgresql://$host:$port/$database"

    var connection: Connection? = null

    try {
        // 2. Conectarse a la base de datos
        connection = DriverManager.getConnection(url, dbUser, dbPassword)
        println("Conexión exitosa a la base de datos!")

        // 3. Pedir usuario y contraseña
        print("Usuario: ")
        val inputUser = readLine() ?: ""
        print("Contraseña: ")
        val inputPass = readLine() ?: ""

        // 4. Consulta segura con PreparedStatement
        val sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?"
        val stmt: PreparedStatement = connection.prepareStatement(sql)
        stmt.setString(1, inputUser)
        stmt.setString(2, inputPass)

        val rs: ResultSet = stmt.executeQuery()

        if (rs.next()) {
            println("¡Login exitoso! Bienvenido ${rs.getString("nombre")}")
            val intent = Intent(context, Pagina2::class.java)
            context.startActivity(intent)
        } else {
            println("Error: Usuario o contraseña incorrectos")
        }

        // 5. Cerrar recursos
        rs.close()
        stmt.close()

    } catch (e: Exception) {
        println("Error de conexión o consulta: ${e.message}")
        e.printStackTrace()
    } finally {
        connection?.close()
    }
}

