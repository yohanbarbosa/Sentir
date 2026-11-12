package com.sudo.sentir.basic_navigate
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sudo.sentir.screen.HomeScreen
import com.sudo.sentir.screen.DetailScreen
import kotlinx.serialization.Serializable


@Serializable
data object Home: NavKey
@Serializable
data class Detail(val id:String): NavKey

@Serializable
data object Error: NavKey
@Composable
fun BasicNavigateWrapper(){
//    val backStack = remember { mutableStateListOf<Any>(Home) }
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = {key ->
            when(key){
                is Home -> NavEntry(key){
                    HomeScreen{ id ->
                        backStack.add(Detail(id))
                    }
                }
                is Detail -> NavEntry(key){
                    DetailScreen(key.id){
                        backStack.removeLastOrNull()
                    }
                }
                else -> NavEntry(key = Error){
                    Text("Error :v ")
                }
            }
        }
    )
}