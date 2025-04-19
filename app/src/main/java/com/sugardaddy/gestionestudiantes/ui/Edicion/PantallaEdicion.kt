package com.sugardaddy.gestionestudiantes.ui.editar

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sugardaddy.gestionestudiantes.components.SelectorDesplegable
import com.sugardaddy.gestionestudiantes.data.model.Estudiante
import com.sugardaddy.gestionestudiantes.data.repository.FirebaseRepository
import com.sugardaddy.gestionestudiantes.utils.Validaciones

@Composable
fun PantallaEdicion(navController: NavController, carnet: String) {
    val contexto = LocalContext.current
    var estudiante by remember { mutableStateOf<Estudiante?>(null) }
    var estaCargando by remember { mutableStateOf(true) }
    var estaGuardando by remember { mutableStateOf(false) }

    LaunchedEffect(carnet) {
        FirebaseRepository.obtenerEstudiantePorCarnet(
            carnet,
            onSuccess = {
                estudiante = it
                estaCargando = false
            },
            onError = {
                Toast.makeText(contexto, "Error al cargar estudiante", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        )
    }

    var nombre by remember { mutableStateOf("") }
    var primerApellido by remember { mutableStateOf("") }
    var segundoApellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    LaunchedEffect(estudiante) {
        estudiante?.let {
            nombre = it.nombre
            primerApellido = it.apellido1
            segundoApellido = it.apellido2
            correo = it.email
            telefono = it.telefono
        }
    }
    val planes = listOf("Plan 2019", "Plan 2020", "Plan 2021")
    var planSeleccionado by remember { mutableStateOf(planes.first()) }
    var anioSeleccionado by remember { mutableStateOf("2023") }
    LaunchedEffect(estudiante) {
        estudiante?.let {
            planSeleccionado = it.planEstudios
            anioSeleccionado = it.anioInicio
        }
    }
    val esNombreValido = Validaciones.esNombreValido(nombre)
    val esCorreoValido = Validaciones.esEmailValido(correo)
    val esTelefonoValido = Validaciones.esTelefonoValido(telefono)

    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.size(36.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Editar Estudiante",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (estaCargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                isError = !esNombreValido,
                supportingText = { if (!esNombreValido) Text("Debe tener al menos 3 letras") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = primerApellido,
                onValueChange = { primerApellido = it },
                label = { Text("Primer Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = segundoApellido,
                onValueChange = { segundoApellido = it },
                label = { Text("Segundo Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = carnet,
                onValueChange = {},
                label = { Text("Carnet") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            val anios = (2000..2025).map { it.toString() }
            SelectorDesplegable(anioSeleccionado, anios, "Año de Inicio") { anioSeleccionado = it }
            SelectorDesplegable(planSeleccionado, planes, "Plan de estudios") { planSeleccionado = it }

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Email") },
                isError = !esCorreoValido,
                supportingText = { if (!esCorreoValido) Text("Correo inválido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = !esTelefonoValido,
                supportingText = { if (!esTelefonoValido) Text("Teléfono inválido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (estaGuardando) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            Button(
                onClick = {
                    estaGuardando = true
                    val nuevosDatos = mapOf(
                        "nombre" to nombre,
                        "apellido1" to primerApellido,
                        "apellido2" to segundoApellido,
                        "planEstudios" to planSeleccionado,
                        "email" to correo,
                        "telefono" to telefono,
                        "anioInicio" to anioSeleccionado
                    )
                    FirebaseRepository.actualizarEstudiante(
                        carnet,
                        nuevosDatos,
                        onSuccess = {
                            estaGuardando = false
                            Toast.makeText(contexto, "Estudiante actualizado", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onError = {
                            estaGuardando = false
                            Toast.makeText(contexto, "Error al actualizar: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                enabled = esNombreValido && esCorreoValido && esTelefonoValido && !estaGuardando,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}