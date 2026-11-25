package com.sudo.sentir.ui.solicitudes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Colores ----------
val HeaderColor = Color(0xFF1E293B)
val Fondo = Color(0xFFF1F5F9)
val Texto = Color(0xFF334155)
val TextoSuave = Color(0xFF64748B)
val Link = Color(0xFF1D4ED8)

// ---------- Modelo ----------
enum class Prioridad(val label: String, val fg: Color, val bg: Color) {
    CRITICA("Crítica", Color(0xFFC62828), Color(0xFFFDE2E2)),
    ALTA("Alta", Color(0xFFE65100), Color(0xFFFFE8D6)),
    MEDIA("Media", Color(0xFFB7791F), Color(0xFFFEF3C7))
}

data class Solicitud(
    val codigo: String,
    val tipo: String,
    val direccion: String,
    val personas: Int,
    val prioridad: Prioridad,
    val estado: String,
    val descripcion: String = "",
    val coordenadas: String = ""
)

// Pública (sin private) para que el NavHost y PantallaDetalle puedan usarla
val solicitudes = listOf(
    Solicitud(
        "SOL-104", "Rescate", "Calle 45 # 12-88", 3, Prioridad.CRITICA, "Pendiente",
        descripcion = "Extracción de escombros y atención por colapso de pared interna.",
        coordenadas = "4.6097, -74.0817"
    ),
    Solicitud(
        "SOL-105", "Médica", "Av. Central #89", 1, Prioridad.CRITICA, "Pendiente",
        descripcion = "Atención médica urgente.", coordenadas = "4.6100, -74.0800"
    ),
    Solicitud(
        "SOL-102", "Evacuación", "Carrera 10 # 34", 5, Prioridad.ALTA, "En Proceso",
        descripcion = "Evacuación de familias de zona de riesgo.", coordenadas = "4.6050, -74.0750"
    ),
    Solicitud(
        "SOL-099", "Víveres", "Diagonal 4 # 12", 2, Prioridad.MEDIA, "En Proceso",
        descripcion = "Entrega de alimentos y agua.", coordenadas = "4.6000, -74.0700"
    )
)

// ---------- Pantalla ----------
@Composable
fun SolicitudesScreen(onVerDetalle: (String) -> Unit) {
    var tab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Fondo,
        topBar = { Header() },
        bottomBar = { BarraNavegacion(tab) { tab = it } }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Seccion("RESUMEN DE PRIORIDADES")
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Resumen("04", "CRÍTICA", Prioridad.CRITICA, Modifier.weight(1f))
                    Resumen("09", "ALTA", Prioridad.ALTA, Modifier.weight(1f))
                    Resumen("15", "MEDIA", Prioridad.MEDIA, Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
                Seccion("SOLICITUDES DE ASISTENCIA")
            }
            items(solicitudes) { s ->
                ItemSolicitud(s, onClick = { onVerDetalle(s.codigo) })
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderColor)
            .statusBarsPadding()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "🚨 SGE Móvil",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            "4 CRÍTICAS",
            color = Prioridad.CRITICA.fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Prioridad.CRITICA.bg, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun Seccion(texto: String) {
    Text(texto, color = Texto, fontSize = 12.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun Resumen(numero: String, etiqueta: String, p: Prioridad, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(p.bg, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(numero, color = p.fg, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(etiqueta, color = p.fg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ItemSolicitud(s: Solicitud, onClick: () -> Unit) {
    val estadoColor = when {
        s.estado == "Pendiente" -> Prioridad.CRITICA.fg
        s.prioridad == Prioridad.ALTA -> Prioridad.ALTA.fg
        else -> TextoSuave
    }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.height(IntrinsicSize.Min)) {
            // Franja lateral de color
            Box(
                Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(s.prioridad.fg)
            )
            Column(Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "#${s.codigo} ${s.tipo}",
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        s.prioridad.label,
                        color = s.prioridad.fg,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(s.prioridad.bg, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "${s.direccion} (${s.personas} ${if (s.personas == 1) "Persona" else "Personas"})",
                    color = Texto,
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(6.dp))
                Row {
                    Text(
                        s.estado,
                        color = estadoColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "Ver Detalle →",
                        color = Link,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}   // <-- aquí termina ItemSolicitud

// BarraNavegacion va FUERA de ItemSolicitud, a nivel superior
@Composable
fun BarraNavegacion(seleccionado: Int, onSelect: (Int) -> Unit) {
    val items: List<Pair<String, ImageVector>> = listOf(
        "Solicitudes" to Icons.AutoMirrored.Filled.List,
        "Mapa" to Icons.Default.Map,
        "Navegación" to Icons.Default.Explore,
        "Personas" to Icons.Default.Groups
    )
    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { i, (titulo, icono) ->
            NavigationBarItem(
                selected = seleccionado == i,
                onClick = { onSelect(i) },
                icon = { Icon(icono, contentDescription = titulo) },
                label = { Text(titulo, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Link,
                    selectedTextColor = Link,
                    unselectedIconColor = TextoSuave,
                    unselectedTextColor = TextoSuave,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}