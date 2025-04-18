package com.sugardaddy.gestionestudiantes.utils

object Validaciones {

    fun esNombreValido(texto: String): Boolean = texto.trim().length >= 3

    fun esEmailValido(email: String): Boolean =
        email.contains("@") && email.contains(".")

    fun esTelefonoValido(telefono: String): Boolean =
        telefono.matches(Regex("^\\d{8}$"))

    fun generarCarnet(apellido1: String, apellido2: String, anio: String): String {
        val iniciales = (apellido1.firstOrNull()?.uppercaseChar() ?: 'X').toString() +
                (apellido2.firstOrNull()?.uppercaseChar() ?: 'X')
        val parteNumerica = anio.takeLast(2) + (1000..9999).random()
        return iniciales + parteNumerica
    }
}