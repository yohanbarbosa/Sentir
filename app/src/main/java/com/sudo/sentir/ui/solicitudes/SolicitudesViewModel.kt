package com.sudo.sentir.ui.solicitudes

data class SolicitudesViewModel(
    val codigo: String,
    val tipo: String,
    val direccion: String,
    val personas: Int,
    val prioridad: Prioridad,
    val estado: String,
    val descripcion: String = "",
    val coordenadas: String = ""
)

private val solicitudesViewModel = listOf(
    SolicitudesViewModel(
        "SOL-104", "Rescate", "Calle 45 # 12-88", 3, Prioridad.CRITICA, "Pendiente",
        descripcion = "Extracción de escombros y atención por colapso de pared interna.",
        coordenadas = "4.6097, -74.0817"
    ),
    SolicitudesViewModel("SOL-105", "Médica", "Av. Central #89", 1, Prioridad.CRITICA, "Pendiente",
        descripcion = "Atención médica urgente.", coordenadas = "4.6100, -74.0800"),
    SolicitudesViewModel("SOL-102", "Evacuación", "Carrera 10 # 34", 5, Prioridad.ALTA, "En Proceso",
        descripcion = "Evacuación de familias de zona de riesgo.", coordenadas = "4.6050, -74.0750"),
    SolicitudesViewModel("SOL-099", "Víveres", "Diagonal 4 # 12", 2, Prioridad.MEDIA, "En Proceso",
        descripcion = "Entrega de alimentos y agua.", coordenadas = "4.6000, -74.0700")
)