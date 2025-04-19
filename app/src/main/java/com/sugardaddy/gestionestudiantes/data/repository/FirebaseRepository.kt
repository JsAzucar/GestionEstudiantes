package com.sugardaddy.gestionestudiantes.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sugardaddy.gestionestudiantes.data.model.Estudiante

object FirebaseRepository {
    private val dbRef = FirebaseDatabase.getInstance().getReference("estudiantes")

    fun registrarEstudiante(estudiante: Estudiante, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val id = estudiante.carnet
        dbRef.child(id).setValue(estudiante)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Error desconocido") }
    }
    fun llamarRegistros(onSuccess: (List<Estudiante>) -> Unit, onError: (DatabaseError) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Estudiante>()
                for (objeto in snapshot.children) {
                    val estudiante = objeto.getValue(Estudiante::class.java)
                    estudiante?.let { lista.add(it) }
                }
                onSuccess(lista)
            }
            override fun onCancelled(error: DatabaseError) { onError(error) }
        })
    }
    fun eliminarEstudiante(carnet: String, onSuccess: () -> Unit, onError: (DatabaseError) -> Unit) {
        dbRef.child(carnet).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(DatabaseError.fromException(it)) }
    }

    fun obtenerEstudiantePorCarnet(
        carnet: String,
        onSuccess: (Estudiante) -> Unit,
        onError: (Exception) -> Unit
    ) {
        dbRef.child(carnet).get()
            .addOnSuccessListener { snapshot ->
                snapshot.getValue(Estudiante::class.java)?.let { onSuccess(it) }
                    ?: onError(Exception("Estudiante no encontrado"))
            }
            .addOnFailureListener { onError(it) }
    }

    fun actualizarEstudiante(
        carnet: String,
        nuevosDatos: Map<String, Any>,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        dbRef.child(carnet).updateChildren(nuevosDatos)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}