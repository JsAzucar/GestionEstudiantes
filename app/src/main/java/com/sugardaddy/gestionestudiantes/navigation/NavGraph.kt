package com.sugardaddy.gestionestudiantes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sugardaddy.gestionestudiantes.ui.principal.PantallaPrincipal
import com.sugardaddy.gestionestudiantes.ui.registrar.PantallaRegistro

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "registro") {
        composable("registro") { PantallaRegistro(navController) }
        composable("principal") { PantallaPrincipal(navController) }
    }
}