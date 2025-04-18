package com.sugardaddy.gestionestudiantes.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sugardaddy.gestionestudiantes.ui.theme.GestionEstudiantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorDesplegable(
    valorSeleccionado: String,
    opciones: List<String>,
    etiqueta: String,
    onSeleccionado: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expandido, onExpandedChange = { expandido = !expandido }) {
        OutlinedTextField(
            value = valorSeleccionado,
            onValueChange = {},
            label = { Text(etiqueta) },
            readOnly = true,
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(text = { Text(opcion) }, onClick = {
                    onSeleccionado(opcion)
                    expandido = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaSelector() {
    GestionEstudiantesTheme {
        SelectorDesplegable(
            valorSeleccionado = "Plan 2020",
            opciones = listOf("Plan 2019", "Plan 2020", "Plan 2021"),
            etiqueta = "Plan de estudios",
            onSeleccionado = {}
        )
    }
}
