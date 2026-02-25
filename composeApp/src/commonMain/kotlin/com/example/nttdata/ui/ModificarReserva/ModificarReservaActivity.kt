package com.example.nttdata.ui.ModificarReserva

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.ui.RealizarReserva.ClickableReservationField
import com.example.nttdata.ui.RealizarReserva.ReservationField
import kotlinx.coroutines.launch
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import kotlin.reflect.KClass

class paginaModificarReservaScreen(val reserva: Any) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ModificarReservaViewModel = viewModel(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                    return ModificarReservaViewModel(reserva) as T
                }
            }
        )
        ModificarScreen(viewModel)
    }
}

@Composable
fun ModificarScreen(viewModel: ModificarReservaViewModel) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // PICKERS: Eliminados los nombres de parámetros conflictivos (onDismissRequest/onDismiss)
    // Se usa la estructura simplificada que acepta la mayoría de versiones KMP

    if (showDatePicker) {
        WheelDatePickerView(
            modifier = Modifier.fillMaxWidth(),
            showDatePicker = showDatePicker,
            title = "Selecciona Fecha",
            doneLabel = "Aceptar",
            height = 200.dp, // Obligatorio en 1.0.7
            onDoneClick = { localDate ->
                viewModel.onFechaChanged(localDate.toString())
                showDatePicker = false
            },
            onDismiss = { // Obligatorio en 1.0.7
                showDatePicker = false
            }
        )
    }

    if (showTimePicker) {
        WheelTimePickerView(
            modifier = Modifier.fillMaxWidth(),
            showTimePicker = showTimePicker,
            title = "Selecciona Hora",
            doneLabel = "Aceptar",
            height = 200.dp, // Obligatorio en 1.0.7
            onDoneClick = { localTime ->
                viewModel.onHoraChanged(localTime.toString().take(5))
                showTimePicker = false
            },
            onDismiss = { // Obligatorio en 1.0.7
                showTimePicker = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Text(
                text = "Modificar Reserva",
                color = Color(0xFF0072BB),
                fontSize = 22.sp,
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
                placeholder = "Seleccione fecha",
                onClick = { showDatePicker = true }
            )

            ClickableReservationField(
                label = "Hora",
                value = viewModel.hora,
                placeholder = "Seleccione hora",
                onClick = { showTimePicker = true }
            )

            if (viewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (viewModel.confirmarCambios()) {
                            navigator.pop()
                        }
                    }
                },
                enabled = !viewModel.isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                if (viewModel.isSaving) {
                    // Corregido: CircularProgressIndicator no usa 'height', usa Modifier.size
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Confirmar cambios", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}