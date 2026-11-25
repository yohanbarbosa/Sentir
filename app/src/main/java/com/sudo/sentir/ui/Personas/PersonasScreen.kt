package com.sudo.sentir.ui.Personas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudo.sentir.ui.detalleSolicitud.Etiqueta
import com.sudo.sentir.ui.detalleSolicitud.TarjetaBlanca
import com.sudo.sentir.ui.solicitudes.BarraNavegacion
import com.sudo.sentir.ui.solicitudes.HeaderColor
import com.sudo.sentir.ui.solicitudes.Seccion
import com.sudo.sentir.ui.solicitudes.Texto
import com.sudo.sentir.ui.solicitudes.TextoSuave

// ---------- Modelo ----------
enum class EstadoPersona(
    val label: String,
    val emoji: String,
    val fg: Color,
    val bg: Color
) {
    HERIDA("Herida", "🚨", Color(0xFFC62828), Color(0xFFFDE2E2)),
    ESTABLE("Estable", "⚖️", Color(0xFFB45309), Color(0xFFFFE8D6)),
    EVACUADA("Evacuada", "🚑", Color(0xFF1D4ED8), Color(0xFFDBEAFE)),
    ATENDIDA("Atendida", "✅", Color(0xFF15803D), Color(0xFFDCFCE7))
}

data class Persona(
    val id: Int,
    val nombre: String,
    val detalle: String,
    val estado: EstadoPersona,
    val observacion: String = ""
)

// ---------- Pantalla ----------
@Composable
fun PersonasScreen(
    codigoSolicitud: String,
    personas: List<Persona>,
    onGuardar: (Persona) -> Unit,
    onFinalizar: () -> Unit,
    onTabSelected: (Int) -> Unit = {}
) {
    var seleccionada by remember { mutableIntStateOf(0) }

    var estadoSel by remember(seleccionada) { mutableStateOf(personas[seleccionada].estado) }
    var observacion by remember(seleccionada) { mutableStateOf(personas[seleccionada].observacion) }
    // ... el resto igual
    Scaffold(
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HeaderColor)
                    .statusBarsPadding()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "👤 Registro de Afectados",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = { BarraNavegacion(seleccionado = 3, onSelect = onTabSelected) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Seccion("SELECCIONAR PERSONA (#$codigoSolicitud)")

                personas.forEachIndexed { i, p ->
                    TarjetaPersona(
                        persona = p,
                        seleccionada = i == seleccionada,
                        onClick = { seleccionada = i }
                    )
                }

                Spacer(Modifier.height(4.dp))
                Seccion("CAMBIAR ESTADO SELECCIONADO:")

                val opciones = EstadoPersona.entries
                opciones.chunked(2).forEach { fila ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        fila.forEach { e ->
                            BotonEstado(
                                estado = e,
                                activo = e == estadoSel,
                                onClick = { estadoSel = e },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Observación técnica
                TarjetaBlanca() {
                    Etiqueta("OBSERVACIÓN TÉCNICA")
                    BasicTextField(
                        value = observacion,
                        onValueChange = { observacion = it },
                        textStyle = TextStyle(fontSize = 13.sp, color = Color(0xFF0F172A)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 48.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = {
                    onGuardar(
                        personas[seleccionada].copy(
                            estado = estadoSel,
                            observacion = observacion
                        )
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Estado de Persona", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = onFinalizar,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar asistencia", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ---------- Componentes ----------
@Composable
fun TarjetaPersona(persona: Persona, seleccionada: Boolean, onClick: () -> Unit) {
    val borde = if (seleccionada) Color(0xFF3B82F6) else Color(0xFFCBD5E1)
    val fondo = if (seleccionada) Color(0xFFEFF6FF) else Color.White

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo, RoundedCornerShape(8.dp))
            .border(if (seleccionada) 1.5.dp else 1.dp, borde, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                persona.nombre,
                color = Color(0xFF0F172A),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                persona.estado.label,
                color = persona.estado.fg,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(persona.estado.bg, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )
        }
        Text(
            persona.detalle + if (seleccionada) " • Seleccionado" else "",
            color = TextoSuave,
            fontSize = 12.sp
        )
    }
}

@Composable
fun BotonEstado(
    estado: EstadoPersona,
    activo: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(if (activo) estado.bg else Color.White, RoundedCornerShape(8.dp))
            .border(
                if (activo) 2.dp else 1.dp,
                if (activo) estado.fg else Color(0xFFCBD5E1),
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "${estado.emoji} ${estado.label}",
            color = if (activo) estado.fg else Texto,
            fontSize = 13.sp,
            fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal
        )
    }
}