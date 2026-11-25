package com.sudo.sentir.ui.solicitudes

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.sudo.sentir.ui.Personas.EstadoPersona
import com.sudo.sentir.ui.Personas.Persona

class SolicitudesViewModel : ViewModel() {

    private val personasPorSolicitud = mutableStateMapOf<String, SnapshotStateList<Persona>>()
    private val inicios = mutableMapOf<String, Long>()

    /** Crea la lista la primera vez (según s.personas) y la reutiliza después. */
    fun personasDe(s: Solicitud): SnapshotStateList<Persona> =
        personasPorSolicitud.getOrPut(s.codigo) {
            inicios[s.codigo] = System.currentTimeMillis()   // inicia el cronómetro
            List(s.personas) { i ->
                Persona(i + 1, "Persona #${i + 1}", "Edad no registrada", EstadoPersona.HERIDA)
            }.toMutableStateList()
        }

    fun guardar(codigo: String, persona: Persona) {
        val lista = personasPorSolicitud[codigo] ?: return
        val idx = lista.indexOfFirst { it.id == persona.id }
        if (idx >= 0) lista[idx] = persona
    }

    /** true cuando ya nadie está en estado Herida. */
    fun asistenciaCompleta(codigo: String): Boolean =
        personasPorSolicitud[codigo]?.none { it.estado == EstadoPersona.HERIDA } ?: false

    fun minutosRespuesta(codigo: String): Int {
        val inicio = inicios[codigo] ?: return 0
        return ((System.currentTimeMillis() - inicio) / 60_000).toInt().coerceAtLeast(1)
    }
}