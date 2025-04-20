package com.sugardaddy.gestionestudiantes.ui.mostrar

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sugardaddy.gestionestudiantes.data.model.Estudiante
import com.sugardaddy.gestionestudiantes.data.repository.FirebaseRepository
import androidx.compose.ui.platform.LocalContext

@Composable
fun ListaEstudiantes(navController: NavController) {
    var listaEstudiantes by remember { mutableStateOf<List<Estudiante>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        FirebaseRepository.llamarRegistros(
            onSuccess = { listaEstudiantes = it },
            onError = { Toast.makeText(context, "Error leyendo los registros", Toast.LENGTH_LONG).show() }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(listaEstudiantes) { estudiante ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Nombre: ${estudiante.nombre} ${estudiante.apellido1} ${estudiante.apellido2}")
                    Text("Carnet: ${estudiante.carnet}")
                    Text("Plan: ${estudiante.planEstudios}")
                    Text("Email: ${estudiante.email}")
                    Text("Teléfono: ${estudiante.telefono}")
                    Text("Inicio: ${estudiante.anioInicio}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            navController.navigate("editar/${estudiante.carnet}")
                        }) {
                            Text("Editar")
                        }
                        Button(onClick = {
                            FirebaseRepository.eliminarEstudiante(estudiante.carnet,
                                onSuccess = {
                                    listaEstudiantes = listaEstudiantes.filter {
                                        it.carnet != estudiante.carnet
                                    }
                                    Toast.makeText(context, "Eliminado con exito", Toast.LENGTH_SHORT).show()
                                },
                                onError = {
                                    Toast.makeText(context, "Error cargando datos", Toast.LENGTH_LONG).show()
                                }
                            )
                        }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}