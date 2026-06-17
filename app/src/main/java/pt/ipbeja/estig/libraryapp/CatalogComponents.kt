/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Header section of the catalog displaying the app name.
 */
@Composable
fun TopHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "LibraryApp",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Search bar component for filtering resources.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Pesquisar") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Ícone de Pesquisa") },
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}

/**
 * Tabs component for switching between resource categories.
 * @param selectedTabIndex The currently selected tab.
 * @param onTabSelected Callback invoked when a tab is clicked.
 */
@Composable
fun TabsSection(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Livros", "Multimédia")

    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
            )
        }
    }
}

/**
 * Filters component for refining the search results using checkboxes.
 */
@Composable
fun FiltersSection() {
    var temaSelecionado by remember { mutableStateOf(false) }
    var anoSelecionado by remember { mutableStateOf(false) }
    var filtrosSelecionado by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = temaSelecionado,
                onCheckedChange = { temaSelecionado = it }
            )
            Text("Tema", fontSize = 14.sp)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = anoSelecionado,
                onCheckedChange = { anoSelecionado = it }
            )
            Text("Ano Escolar", fontSize = 14.sp)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = filtrosSelecionado,
                onCheckedChange = { filtrosSelecionado = it }
            )
            Text("Filtros", fontSize = 14.sp)
        }
    }
}