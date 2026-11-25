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
import com.sudo.sentir.ui.detalleSolicitud.DetalleSolicitudScreen
import com.sudo.sentir.ui.solicitudes.solicitudes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = "lista") {
                    composable("lista") {
                        SolicitudesScreen(onVerDetalle = { codigo ->
                            nav.navigate("detalle/$codigo")   // push a la pila
                        })
                    }
                    composable("detalle/{codigo}") { entry ->
                        val codigo = entry.arguments?.getString("codigo")
                        val s = solicitudes.first { it.codigo == codigo }
                        DetalleSolicitudScreen(s, onBack = { nav.popBackStack() })  // pop
                    }

                    composable("personas") {
                        PersonasScreen(onTabSelected = { i ->
                            if (i == 0) nav.navigate("lista") {
                                popUpTo("lista") { inclusive = true }
                            }
                        })
                    }

                    composable("lista") {
                        SolicitudesScreen(
                            onVerDetalle = { nav.navigate("detalle/$it") },
                            onTabSelected = { i -> if (i == 3) nav.navigate("personas") }
                        )
                    }
                }
            }
        }
    }
}



