/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Header section of the catalog displaying the app name and scan action.
 */
@Composable
fun TopHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "LibraryApp", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { }) { Icon(Icons.Filled.QrCodeScanner, contentDescription = "Scan") }
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
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}

/**
 * Tabs component for switching between resource categories.
 * * @param selectedTabIndex The currently selected tab.
 * @param onTabSelected Callback invoked when a tab is clicked.
 */
@Composable
fun TabsSection(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Books", "Multimedia")
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
 * Filters component for refining the search results.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(selected = false, onClick = { }, label = { Text("Subject") })
        FilterChip(selected = false, onClick = { }, label = { Text("School Year") })
        FilterChip(selected = false, onClick = { }, label = { Text("Filters") })
    }
}