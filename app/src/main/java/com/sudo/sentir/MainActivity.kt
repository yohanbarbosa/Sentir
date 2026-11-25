package com.sudo.sentir

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.sudo.sentir.ui.solicitudes.SolicitudesScreen


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sudo.sentir.ui.Personas.PersonasScreen
import com.sudo.sentir.ui.cierre.CierreScreen
import com.sudo.sentir.ui.detalleSolicitud.DetalleSolicitudScreen
import com.sudo.sentir.ui.solicitudes.solicitudes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sudo.sentir.ui.solicitudes.SolicitudesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val nav = rememberNavController()
                val vm: SolicitudesViewModel = viewModel()

                NavHost(navController = nav, startDestination = "lista") {

                    composable("lista") {
                        SolicitudesScreen(
                            onVerDetalle = { nav.navigate("detalle/$it") },
                            onTabSelected = { /* ... */ }
                        )
                    }

                    composable("detalle/{codigo}") { entry ->
                        val codigo = entry.arguments?.getString("codigo")
                        val s = solicitudes.first { it.codigo == codigo }
                        DetalleSolicitudScreen(
                            s = s,
                            onBack = { nav.popBackStack() },
                            onActualizar = { nav.navigate("personas/${s.codigo}") }
                        )
                    }

                    composable("personas/{codigo}") { entry ->
                        val codigo = entry.arguments?.getString("codigo")!!
                        val s = solicitudes.first { it.codigo == codigo }
                        PersonasScreen(
                            codigoSolicitud = codigo,
                            personas = vm.personasDe(s),
                            onGuardar = { persona -> vm.guardar(codigo, persona) },
                            onFinalizar = {
                                nav.navigate("cierre/$codigo") { popUpTo("lista") }
                            }
                        )
                    }

                    composable("cierre/{codigo}") { entry ->
                        val codigo = entry.arguments?.getString("codigo")!!
                        val s = solicitudes.first { it.codigo == codigo }
                        CierreScreen(
                            codigoSolicitud = codigo,
                            personas = vm.personasDe(s),
                            minutos = vm.minutosRespuesta(codigo),
                            onVolver = {
                                nav.navigate("lista") { popUpTo("lista") { inclusive = true } }
                            }
                        )
                    }
                }
            }
        }
    }
}



