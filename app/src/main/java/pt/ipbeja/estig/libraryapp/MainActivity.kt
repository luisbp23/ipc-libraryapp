package pt.ipbeja.estig.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import pt.ipbeja.estig.libraryapp.bibliotecario.AdminApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

private data class NavDestination(val route: String, val icon: ImageVector, val label: String)

private val navDestinations = listOf(
    NavDestination("Home", Icons.Filled.Home, "Início"),
    NavDestination("Search", Icons.Filled.Search, "Pesquisar"),
    NavDestination("Library", Icons.AutoMirrored.Filled.LibraryBooks, "Meus Livros")
)

@Composable
fun AppNavigation() {
    var userType by remember { mutableStateOf<String?>(null) }

    when (userType) {
        null -> UserSelectionScreen(onUserSelected = { userType = it })
        "admin" -> AdminApp()
        "professor", "aluno" -> UserApp(userType = userType!!, onLogout = { userType = null })
    }
}

@Composable
fun UserApp(userType: String, onLogout: () -> Unit) {
    var currentRoute by remember { mutableStateOf("Home") }

    val bookBackStack = remember { mutableStateListOf<Resource>() }
    val selectedBook = bookBackStack.lastOrNull()
    val myLoans = remember { mutableStateListOf<Loan>() }

    val goHome = {
        currentRoute = "Home"
        bookBackStack.clear()
    }

    if (selectedBook != null) {
        BookDetailsScreen(
            resource = selectedBook,
            userType = userType, // Passamos o tipo de utilizador
            onBack = { bookBackStack.removeLastOrNull() },
            onAlternativeClick = { bookBackStack.add(it) },
            onRequisitar = { res ->
                if (myLoans.none { it.id == res.id }) {
                    myLoans.add(Loan(res.id, res.title, res.author, res.imageResId, daysLeft = 15))
                }
            },
            onDownload = { res ->
                val existing = myLoans.find { it.id == res.id }
                if (existing != null) existing.isDownloaded = true
                else myLoans.add(Loan(res.id, res.title, res.author, res.imageResId, daysLeft = 15, isDownloaded = true))
            },
            myLoans = myLoans
        )
        return
    }

    Scaffold(
        bottomBar = {
            AppBottomBar(currentRoute = currentRoute, onRouteSelected = { currentRoute = it; bookBackStack.clear() })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentRoute) {
                "Home" -> CatalogScreen(onBookClick = { bookBackStack.add(it) })
                "Search" -> SearchScreen(onBookClick = { bookBackStack.add(it) })
                "Library" -> LoansScreen(myLoans = myLoans, userType = userType) // Passamos o tipo de utilizador
            }
        }
    }
}

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