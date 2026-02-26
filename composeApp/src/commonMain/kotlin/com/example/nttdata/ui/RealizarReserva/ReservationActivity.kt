package com.example.nttdata.ui.RealizarReserva

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.nttdata.SesionManager.SessionManager
import com.example.nttdata.ui.SeleccionarSitio.SeleccionAsientoActivity
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView

class ReservationActivityScreen(val reservationViewModel: ReservationViewModel = ReservationViewModel()): Screen {
    @Composable
    override fun Content() {
        ReservationScreen(viewModel = reservationViewModel)
    }
}

@Composable
fun ReservationScreen(viewModel: ReservationViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val navigator: Navigator = LocalNavigator.currentOrThrow

    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

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
        showTimePicker = showStartTimePicker,
        title = "Selecciona Hora Comienzo",
        doneLabel = "Aceptar",
        height = 200.dp,
        onDoneClick = { localTime ->
            viewModel.onHoraInicioChanged(localTime.toString())
            showStartTimePicker = false
        },
        onDismiss = { showStartTimePicker = false }
    )

    WheelTimePickerView(
        modifier = Modifier.fillMaxWidth(),
        showTimePicker = showEndTimePicker,
        title = "Selecciona Hora Finalizar",
        doneLabel = "Aceptar",
        height = 200.dp,
        onDoneClick = { localTime ->
            viewModel.onHoraChangedFinal(localTime.toString())
            showEndTimePicker = false
        },
        onDismiss = { showEndTimePicker = false }
    )

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
                text = "Introduce datos para la reserva:",
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
                onClick = { showDatePicker = true }
            )

            ClickableReservationField(
                label = "Hora Inicio",
                value = viewModel.horaInicio,
                placeholder = "Seleccione una hora inicio",
                onClick = { showStartTimePicker = true }
            )

            ClickableReservationField(
                label = "Hora Fin",
                value = viewModel.horaFinal,
                placeholder = "Seleccione una hora Final",
                onClick = { showEndTimePicker = true }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navigator.push(SeleccionAsientoActivity(viewModel.fecha, viewModel.horaInicio, viewModel.horaFinal))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072BB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Seleccionar sitio",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ReservationField(label: String, value: String, onValueChange: (String) -> Unit, readOnly: Boolean) {
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
            onValueChange = onValueChange,
            readOnly = readOnly,
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
fun ClickableReservationField(label: String, value: String, placeholder: String, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            color = Color(0xFF0072BB),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (value.isEmpty()) placeholder else value,
                color = if (value.isEmpty()) Color.Gray else Color.Black,
                fontSize = 16.sp
            )
        }
    }
}