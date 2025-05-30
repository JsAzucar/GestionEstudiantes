package com.sugardaddy.gestionestudiantes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sugardaddy.gestionestudiantes.ui.editar.PantallaEdicion
import com.sugardaddy.gestionestudiantes.ui.principal.PantallaPrincipal
import com.sugardaddy.gestionestudiantes.ui.registrar.PantallaRegistro
import com.sugardaddy.gestionestudiantes.ui.mostrar.ListaEstudiantes

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "principal") {
        composable("registro") { PantallaRegistro(navController) }
        composable("principal") { PantallaPrincipal(navController) }
        composable("mostrarEditar") { ListaEstudiantes(navController) }
        composable("editar/{carnet}") { backStackEntry ->
            val carnet = backStackEntry.arguments?.getString("carnet") ?: ""
            PantallaEdicion(navController, carnet)
        }
    }
}