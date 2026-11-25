package com.sudo.sentir.ui.detalleSolicitud

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudo.sentir.ui.solicitudes.HeaderColor
import com.sudo.sentir.ui.solicitudes.Link
import com.sudo.sentir.ui.solicitudes.Prioridad
import com.sudo.sentir.ui.solicitudes.Solicitud
import com.sudo.sentir.ui.solicitudes.TextoSuave

@Composable
fun DetalleSolicitudScreen(s: Solicitud, onBack: () -> Unit) {
    Scaffold (
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HeaderColor)
                    .statusBarsPadding()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "←",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable() { onBack() }
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    "Detalle #${s.codigo}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    s.prioridad.label.uppercase(),
                    color = s.prioridad.fg,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(s.prioridad.bg, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        },
        bottomBar = {
            Button(
                onClick = { /* abrir mapa/navegación */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Text("Trazar Ruta de Navegación →", fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Tipo de emergencia
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(s.prioridad.bg, RoundedCornerShape(8.dp))
                    .border(1.dp, s.prioridad.fg.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Etiqueta("TIPO DE EMERGENCIA", s.prioridad.fg)
                Text(
                    "${s.tipo} / Atrapado en Estructura",
                    color = Color(0xFF7F1D1D),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Necesidad principal
            TarjetaBlanca {
                Etiqueta("NECESIDAD PRINCIPAL")
                Text(s.descripcion, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }

            // Personas + estado
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TarjetaBlanca(Modifier.weight(1f)) {
                    Etiqueta("PERSONAS AFECTADAS")
                    Text("${s.personas} Afectados", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Link)
                }
                TarjetaBlanca(Modifier.weight(1f)) {
                    Etiqueta("ESTADO ACTUAL")
                    Text(
                        if (s.estado == "Pendiente") "Sin Atender" else s.estado,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (s.estado == "Pendiente") Prioridad.CRITICA.fg else Prioridad.ALTA.fg
                    )
                }
            }

            // Ubicación
            TarjetaBlanca {
                Etiqueta("UBICACIÓN EXACTA")
                Text("${s.direccion}, Sector Norte", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                Text("Coordenadas: ${s.coordenadas}", fontSize = 11.sp, color = TextoSuave)
            }
        }
    }
}

@Composable
fun Etiqueta(texto: String, color: Color = TextoSuave) {
    Text(texto, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun TarjetaBlanca(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(8.dp))
            .padding(12.dp),
        content = content
    )
}