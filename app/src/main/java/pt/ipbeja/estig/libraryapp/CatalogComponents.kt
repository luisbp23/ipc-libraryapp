/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * Tabs component for switching between resource categories with custom colors.
 *
 * @param selectedTabIndex The currently selected tab.
 * @param onTabSelected Callback invoked when a tab is clicked.
 */
@Composable
fun TabsSection(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Livros", "Multimédia")

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

/**
 * Filters component for refining the search results using dropdown menus.
 */
@Composable
fun FiltersSection() {
    val temas = listOf("Ficção", "Aventura", "Ciência", "História", "Romance", "Poesia")
    val anos = listOf("1º Ano", "2º Ano", "3º Ano", "4º Ano", "5º Ano", "6º Ano", "7º Ano", "8º Ano", "9º Ano")
    val filtros = listOf("Mais Recentes", "A-Z", "Melhor Avaliação")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()), // Permite arrastar os filtros para os lados!
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DropdownFilterItem(label = "Tema", options = temas)
        DropdownFilterItem(label = "Ano Escolar", options = anos)
        DropdownFilterItem(label = "Filtros", options = filtros)
    }
}

/**
 * Individual dropdown filter component.
 *
 * @param label The default label for the filter.
 * @param options The list of options to display in the dropdown.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFilterItem(label: String, options: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(label) }

    Box {
        FilterChip(
            selected = selectedOption != label,
            onClick = { expanded = true },
            label = { Text(selectedOption) },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = "Expandir $label"
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Qualquer $label", fontWeight = FontWeight.Bold) },
                onClick = {
                    selectedOption = label
                    expanded = false
                }
            )
            HorizontalDivider()

            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }
}