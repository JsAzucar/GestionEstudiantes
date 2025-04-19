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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pantalla Principal")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
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