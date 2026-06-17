/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * Main entry point of the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

/**
 * Handles the main bottom navigation and screen routing.
 */
@Composable
fun AppNavigation() {
    var currentRoute by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onRouteSelected = { route -> currentRoute = route }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentRoute) {
                "Home" -> CatalogScreen()
                "Search" -> SearchScreen()
                "Library" -> LoansScreen()
            }
        }
    }
}

/**
 * Composable that represents the Bottom Navigation Bar.
 */
@Composable
fun AppBottomBar(currentRoute: String, onRouteSelected: (String) -> Unit) {
    BottomAppBar {
        NavigationBarItem(
            selected = currentRoute == "Home",
            onClick = { onRouteSelected("Home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            selected = currentRoute == "Search",
            onClick = { onRouteSelected("Search") },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Pesquisar") },
            label = { Text("Pesquisar") }
        )
        NavigationBarItem(
            selected = currentRoute == "Library",
            onClick = { onRouteSelected("Library") },
            icon = { Icon(Icons.Filled.LocalLibrary, contentDescription = "Biblioteca") },
            label = { Text("Biblioteca") }
        )
    }
}