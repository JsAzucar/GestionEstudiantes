package com.sugardaddy.gestionestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.sugardaddy.gestionestudiantes.navigation.NavGraph
import com.sugardaddy.gestionestudiantes.ui.theme.GestionEstudiantesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionEstudiantesTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
