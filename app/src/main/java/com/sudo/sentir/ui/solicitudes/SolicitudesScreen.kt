package com.sudo.sentir.ui.solicitudes


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
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
private val HeaderColor = Color(0xFF1E293B)
private val Fondo = Color(0xFFF1F5F9)
private val Texto = Color(0xFF334155)
private val TextoSuave = Color(0xFF64748B)
private val Link = Color(0xFF1D4ED8)

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
    val estado: String
)

private val solicitudes = listOf(
    Solicitud("SOL-104", "Rescate", "Calle 45 # 12-88", 3, Prioridad.CRITICA, "Pendiente"),
    Solicitud("SOL-105", "Médica", "Av. Central #89", 1, Prioridad.CRITICA, "Pendiente"),
    Solicitud("SOL-102", "Evacuación", "Carrera 10 # 34", 5, Prioridad.ALTA, "En Proceso"),
    Solicitud("SOL-099", "Víveres", "Diagonal 4 # 12", 2, Prioridad.MEDIA, "En Proceso")
)



// ---------- Pantalla ----------
@Composable
fun SolicitudesScreen() {
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
            items(solicitudes) { ItemSolicitud(it) }
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
fun ItemSolicitud(s: Solicitud) {
    val estadoColor = when {
        s.estado == "Pendiente" -> Prioridad.CRITICA.fg
        s.prioridad == Prioridad.ALTA -> Prioridad.ALTA.fg
        else -> TextoSuave
    }

    Card(
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
}

@Composable
fun BarraNavegacion(seleccionado: Int, onSelect: (Int) -> Unit) {
    val items: List<Pair<String, ImageVector>> = listOf(
        "Solicitudes" to Icons.Default.List,
        "Mapa" to Icons.Default.Place,
        "Navegación" to Icons.Default.LocationOn,
        "Personas" to Icons.Default.Person
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
