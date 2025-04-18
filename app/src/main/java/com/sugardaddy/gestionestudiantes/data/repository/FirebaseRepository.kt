package com.sugardaddy.gestionestudiantes.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.sugardaddy.gestionestudiantes.data.model.Estudiante

object FirebaseRepository {
    private val dbRef = FirebaseDatabase.getInstance().getReference("estudiantes")

    fun registrarEstudiante(estudiante: Estudiante, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val id = estudiante.carnet
        dbRef.child(id).setValue(estudiante)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Error desconocido") }
    }
}