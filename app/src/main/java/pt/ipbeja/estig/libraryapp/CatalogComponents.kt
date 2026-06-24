package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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

@Composable
fun TopHeader(onUserIconClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("LibraryApp", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Perfil",
            modifier = Modifier.size(36.dp).clickable { onUserIconClick() },
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Pesquisa") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Ícone") },
        singleLine = true,
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
        )
    )
}

@Composable
fun TabsSection(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Livros", "Multimédia")
    TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.primary) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Composable
fun FiltersSection(
    selectedTabIndex: Int,
    selectedAno: String, onAnoSelected: (String) -> Unit,
    selectedTema: String, onTemaSelected: (String) -> Unit,
    selectedFiltro: String, onFiltroSelected: (String) -> Unit
) {
    val anos = listOf("9.º Ano", "11.º Ano", "12.º Ano", "Geral")
    val temas = listOf("Ficção Científica", "Tecnologia", "Documentário")
    val filtros = listOf("Mais Recentes", "A-Z")

    Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (selectedTabIndex == 0) {
            DropdownFilterItem("Ano Esc.", anos, selectedAno, onAnoSelected)
        } else {
            DropdownFilterItem("Tema", temas, selectedTema, onTemaSelected)
        }
        DropdownFilterItem("Filtros", filtros, selectedFiltro, onFiltroSelected)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFilterItem(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        FilterChip(
            selected = selectedOption != label,
            onClick = { expanded = true },
            label = { Text(selectedOption) },
            trailingIcon = { Icon(imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown, contentDescription = "Expandir") }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Qualquer $label", fontWeight = FontWeight.Bold) }, onClick = { onOptionSelected(label); expanded = false })
            HorizontalDivider()
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = { onOptionSelected(option); expanded = false })
            }
        }
    }
}