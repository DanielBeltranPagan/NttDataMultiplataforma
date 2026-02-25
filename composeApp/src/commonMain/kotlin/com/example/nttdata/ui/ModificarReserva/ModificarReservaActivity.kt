package com.example.nttdata.ui.ModificarReserva

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.ui.RealizarReserva.ClickableReservationField
import com.example.nttdata.ui.RealizarReserva.ReservationField
import com.example.nttdata.ui.RealizarReserva.ClickableReservationField

import kotlinx.coroutines.launch
import nttdata.composeapp.generated.resources.Res
import nttdata.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

class paginaModificarReservaScreen(
    //val viewModel : ModificarViewModel
): Screen{
    @Composable
    override fun Content() {
        ModificarScreen()
    }
}

@Composable
fun ModificarScreen(viewModel : ModificarReservaViewModel= viewModel()) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }


    WheelDatePickerView(
        modifier = Modifier.fillMaxWidth(),
        showDatePicker = showDatePicker,
        title = "Selecciona Fecha",
        doneLabel = "Aceptar",
        height = 200.dp,
        onDoneClick = { localDate ->
            viewModel.onFechaChanged(localDate.toString())
            showDatePicker = false
        },
        onDismiss = { showDatePicker = false }
    )


    WheelTimePickerView(
        modifier = Modifier.fillMaxWidth(),
        showTimePicker = showTimePicker,
        title = "Selecciona Hora",
        doneLabel = "Aceptar",
        height = 200.dp, //
        onDoneClick = { localTime ->
            viewModel.onHoraChanged(localTime.toString())
            showTimePicker = false
        },
        onDismiss = { showTimePicker = false }
    )


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
                text = "Modifica los datos de la reserva:",
                color = Color(0xFF0072BB),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            ReservationField(
                label = "Sucursal",
                value = SessionManager.nombreSucursal,
                onValueChange = {},
                readOnly = true
            )
            ClickableReservationField(
                label = "Fecha",
                value = viewModel.fecha,
                placeholder = "Seleccione una fecha",
                onClick = {
                    showDatePicker=true

                }
            )
            ClickableReservationField(
                label = "Hora",
                value = viewModel.hora,
                placeholder = "Seleccione una hora",
                onClick = {
                    showTimePicker=true

                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Reserva cancelada")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Confirmar cambios",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

    }
}

@Composable
fun ModificarField(label: String, value: String) {
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

@Composable
fun PreviewModificarScreen() {
    ModificarScreen()
}