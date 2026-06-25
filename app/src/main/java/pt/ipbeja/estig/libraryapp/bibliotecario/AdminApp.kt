package pt.ipbeja.estig.libraryapp.bibliotecario

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipbeja.estig.libraryapp.CatalogScreen
import pt.ipbeja.estig.libraryapp.CreamBeige
import pt.ipbeja.estig.libraryapp.DarkBurgundy
import pt.ipbeja.estig.libraryapp.WeakRed

@Composable
fun AdminApp() {
    var currentRoute by remember { mutableStateOf("Biblioteca") }
    var showAddMenu by remember { mutableStateOf(false) }
    var selectedResourceType by remember { mutableStateOf("livro") }

    var showExitDialog by remember { mutableStateOf(false) }
    var pendingResourceType by remember { mutableStateOf("") }
    var books by remember { mutableStateOf(listOf(BookForm())) }
    var medias by remember { mutableStateOf(listOf(MediaForm())) }

    BackHandler(enabled = currentRoute != "Biblioteca") {
        currentRoute = "Biblioteca"
    }

    BackHandler(enabled = currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            containerColor = CreamBeige,
            titleContentColor = DarkBurgundy,
            textContentColor = DarkBurgundy,
            title = { Text("Sair do formulário?", fontWeight = FontWeight.Bold) },
            text = { Text("Os dados inseridos serão perdidos.", fontSize = 16.sp) },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    books = listOf(BookForm())
                    medias = listOf(MediaForm())
                    selectedResourceType = if (pendingResourceType.isNotEmpty()) pendingResourceType else selectedResourceType
                    pendingResourceType = ""
                    currentRoute = "Biblioteca"
                }) { Text("Sair", fontSize = 18.sp, color = DarkBurgundy) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    pendingResourceType = ""
                }) { Text("Cancelar", fontSize = 18.sp, color = DarkBurgundy) }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                AdminBottomBar(
                    currentRoute = if (showAddMenu) "Novo" else currentRoute,
                    selectedResourceType = selectedResourceType,
                    onRouteSelected = { route ->
                        if (route == "Novo") {
                            showAddMenu = true
                        } else if (currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") {
                            showExitDialog = true
                        } else {
                            currentRoute = route
                        }
                    }
                )
                }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                when (currentRoute) {
                    "Biblioteca" -> CatalogScreen()
                    "Gestao" -> ManagementScreen()
                    "AdicionarRecurso" -> AddResourceScreen(
                        resourceType = selectedResourceType,
                        books = books,
                        medias = medias,
                        onBooksChange = { books = it },
                        onMediasChange = { medias = it },
                        onShowExitDialog = { showExitDialog = true },
                        onNavigateToVerify = { currentRoute = "VerificarDados" }
                    )
                    "VerificarDados" -> VerifyDataScreen(
                        books = books,
                        medias = medias,
                        resourceType = selectedResourceType,
                        onBack = { currentRoute = "AdicionarRecurso" },
                        onValidate = {
                            // reset dos dados ao validar
                            books = listOf(BookForm())
                            medias = listOf(MediaForm())
                            currentRoute = "Biblioteca"
                        }
                    )
                }
            }
        }
        AddResourceOverlay(
            isVisible = showAddMenu,
            onDismiss = { showAddMenu = false },
            onNavigateToAddResource = { tipo ->
                showAddMenu = false
                if (currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") {
                    pendingResourceType = tipo
                    showExitDialog = true
                } else {
                    selectedResourceType = tipo
                    books = listOf(BookForm())
                    medias = listOf(MediaForm())
                    currentRoute = "AdicionarRecurso"
                }
            }
        )
    }
}

@Composable
fun AdminBottomBar(
    currentRoute: String,
    selectedResourceType: String,
    onRouteSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CreamBeige)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF393939))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AdminBottomNavItem(
                label = "Biblioteca",
                icon = Icons.Filled.LocalLibrary,
                isSelected = currentRoute == "Biblioteca"
            ) { onRouteSelected("Biblioteca") }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onRouteSelected("Novo") }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(54.dp)
                        .border(3.dp, DarkBurgundy, CircleShape)
                        .background(
                            if (currentRoute == "Novo" || currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") DarkBurgundy else Color.Transparent,
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = when {
                            (currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") && selectedResourceType == "livro" -> Icons.Filled.Book
                            (currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") && selectedResourceType == "multimedia" -> Icons.Filled.PlayArrow
                            else -> Icons.Filled.Add
                        },
                        contentDescription = "Novo",
                        modifier = Modifier.size(32.dp),
                        tint = if (currentRoute == "Novo" || currentRoute == "AdicionarRecurso" || currentRoute == "VerificarDados") CreamBeige else DarkBurgundy
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Novo",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBurgundy
                )
            }
            AdminBottomNavItem(
                label = "Gestão",
                icon = Icons.Filled.Settings,
                isSelected = currentRoute == "Gestao"
            ) { onRouteSelected("Gestao") }
        }
    }
}

@Composable
fun RowScope.AdminBottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = if (isSelected) WeakRed else Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 20.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(26.dp),
                tint = DarkBurgundy
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = DarkBurgundy
        )
    }
}

@Composable
fun AddResourceOverlay(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onNavigateToAddResource: (String) -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { onDismiss() }
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 110.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                ResourceTypeButton(icon = Icons.Default.Book, onClick = { onNavigateToAddResource("livro") })
                ResourceTypeButton(icon = Icons.Default.PlayArrow, onClick = { onNavigateToAddResource("multimedia") })
            }
        }
    }
}

@Composable
fun ResourceTypeButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(CreamBeige)
            .border(width = 3.dp, color = DarkBurgundy, shape = CircleShape)
            .clickable { onClick() }
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = DarkBurgundy, modifier = Modifier.size(36.dp))
    }
}