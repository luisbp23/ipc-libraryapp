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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import pt.ipbeja.estig.libraryapp.bibliotecario.AdminApp

/**
 * Main entry point of the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryAppTheme {
                AppNavigation()
            }
        }
    }
}

private data class NavDestination(val route: String, val icon: ImageVector, val label: String)

private val navDestinations = listOf(
    NavDestination("Home", Icons.Filled.Home, "Início"),
    NavDestination("Search", Icons.Filled.Search, "Pesquisar"),
    NavDestination("Library", Icons.Filled.LocalLibrary, "Biblioteca")
)

@Composable
fun AppNavigation() {
    @Composable
    fun AppNavigation() {
        var userType by remember { mutableStateOf<String?>(null) }

        when (userType) {
            null -> UserSelectionScreen(
                onUserSelected = { userType = it }
            )
            "admin" -> AdminApp()
            "professor" -> UserApp()
            "aluno" -> UserApp()
        }
    }
}

/**
 * Handles the main bottom navigation and screen routing.
 */
@Composable
fun UserApp() {
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
 * Bottom Navigation Bar built from [navDestinations].
 */
@Composable
fun AppBottomBar(currentRoute: String, onRouteSelected: (String) -> Unit) {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.surface) {
        val navColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray
        )

        navDestinations.forEach { dest ->
            NavigationBarItem(
                selected = currentRoute == dest.route,
                onClick = { onRouteSelected(dest.route) },
                icon = { Icon(dest.icon, contentDescription = dest.label) },
                label = { Text(dest.label, fontWeight = FontWeight.Bold) },
                colors = navColors
            )
        }
    }
}
