package com.sugardaddy.gestionestudiantes.ui.principal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sugardaddy.gestionestudiantes.ui.theme.GestionEstudiantesTheme

@Composable
fun PantallaPrincipal(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gestión de Estudiantes EIC",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("registro") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Estudiante")
        }

        Button(
            onClick = { navController.navigate("mostrarEditar") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Administrar Estudiante")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VistaPreviaPrincipal() {
    GestionEstudiantesTheme {
        PantallaPrincipal(navController = rememberNavController())
    }
}