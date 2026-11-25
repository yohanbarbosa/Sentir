package com.sudo.sentir.ui.cierre


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudo.sentir.ui.Personas.Persona
import com.sudo.sentir.ui.detalleSolicitud.Etiqueta
import com.sudo.sentir.ui.detalleSolicitud.TarjetaBlanca
import com.sudo.sentir.ui.solicitudes.BarraNavegacion
import com.sudo.sentir.ui.solicitudes.Seccion

private val Verde = Color(0xFF16A34A)

@Composable
fun CierreScreen(
    codigoSolicitud: String,
    personas: List<Persona>,
    minutos: Int,
    onVolver: () -> Unit,
    onTabSelected: (Int) -> Unit = {}
) {
    Scaffold(
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Verde)
                    .statusBarsPadding()
                    .padding(14.dp)
            ) {
                Text("✅ Finalización de Asistencia", color = Color.White,
                    fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        bottomBar = {
            Column {
                Button(
                    onClick = onVolver,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                ) { Text("Regresar a Lista de Solicitudes", fontWeight = FontWeight.Bold) }
                BarraNavegacion(seleccionado = 0, onSelect = onTabSelected)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Banner de éxito
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0FDF4), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF86EFAC), RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("✅", fontSize = 32.sp)
                Text("Atención Completada", color = Color(0xFF166534),
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("Solicitud #$codigoSolicitud actualizada con éxito",
                    color = Verde, fontSize = 11.sp)
            }

            Seccion("RESUMEN DE PERSONAS ATENDIDAS")

            TarjetaBlanca() {
                personas.forEach { p ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                        Text(p.nombre, color = Color(0xFF0F172A), fontSize = 13.sp,
                            modifier = Modifier.weight(1f))
                        Text(p.estado.label, color = p.estado.fg,
                            fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            TarjetaBlanca {
                Etiqueta("TIEMPO TOTAL DE RESPUESTA")
                Text("$minutos Minutos", fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }
        }
    }
}