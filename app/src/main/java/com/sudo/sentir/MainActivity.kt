package com.sudo.sentir

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudo.sentir.basic_navigate.BasicNavigateWrapper


data class Solicitud(
    val id : Int,
    val nombre : String,
    val estado : String,
    val direccion : String
)

val solicitudes = mutableListOf (
    Solicitud(1,"Incendio", "Resuelto","calle 45 #12-30"),
    Solicitud(2,"Incendio", "Pendiente","calle 45 #12-30"),
    Solicitud(3,"Incendio", "Resuelto","calle 45 #12-30"),
    Solicitud(4,"Incendio", "En atencion","calle 45 #12-30"),
    Solicitud(5,"Incendio", "Pendiente","calle 45 #12-30"),
    Solicitud(6,"Incendio", "En atencion","calle 45 #12-30"),
    Solicitud(7,"Incendio", "Pendiente","calle 45 #12-30"),
    )


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent {
           Column (
               modifier = Modifier
                   .fillMaxSize()
                   .background(Color(0xFFe9ecf1))
           ) {
               BasicNavigateWrapper()
           }
        }

    }

}

@Composable
fun TopBar(){
    Text(
        "Solicitudes",
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth())

}
@Composable
fun Table(){
   LazyColumn(
       modifier = Modifier
           .padding(horizontal = 20.dp)
   ) {
       items(solicitudes){solicitud ->

             Column(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(bottom = 12.dp)
                     .background(Color.White)
                     .padding(
                         start = 20.dp,
                         top = 12.dp,
                         end = 20.dp,
                         bottom = 12.dp
                     )
             ){
                 Row(
                     modifier = Modifier
                     .padding(bottom = 10.dp)
                 ){
                     Text(
                         solicitud.id.toString(),
                         fontWeight = FontWeight.Bold,
                         modifier = Modifier
                             .padding(end = 10.dp)
                     )
                     Box(
                         modifier = Modifier

                             .background(
                                 color = when(solicitud.estado){
                                     "Pendiente" -> Color(0xFFfaeeb9)
                                     "En atencion" -> Color(0xFFbfd9f8)
                                     "Resuelto" -> Color(0xFFbfe2ca)
                                     else -> Color.Gray
                                 },
                                 shape = RoundedCornerShape(10.dp)
                             )
                             .padding(
                                 horizontal = 5.dp,
                                 vertical = 2.dp
                             )

                     ){Text(
                         solicitud.estado,
                         fontWeight = FontWeight.Bold
                     )}
                 }
                 Row{
                     Text(
                         text = """
                              ${solicitud.nombre} - ${solicitud.direccion}
                         """.trimIndent()
                     )

                 }
             }

       }
   }

}


@Composable
fun Navigator (){

    Row(
        modifier = Modifier
            .background(Color.White)
    ){

    }
}
