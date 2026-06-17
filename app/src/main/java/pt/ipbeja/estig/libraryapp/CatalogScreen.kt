/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Main catalog screen displaying the available resources.
 */
@Composable
fun CatalogScreen() {
    Scaffold(
        floatingActionButton = { CatalogFloatingActionButton() },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        CatalogContent(Modifier.padding(paddingValues))
    }
}

@Composable
fun CatalogFloatingActionButton() {
    FloatingActionButton(
        onClick = { },
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(50)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add New", tint = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun CatalogContent(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        TopHeader()
        Spacer(modifier = Modifier.height(12.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(12.dp))
        TabsSection(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { newIndex -> selectedTabIndex = newIndex }
        )
        Spacer(modifier = Modifier.height(8.dp))
        FiltersSection()
        Spacer(modifier = Modifier.height(12.dp))

        if (selectedTabIndex == 0) {
            ResourcesGrid()
        } else {
            MultimediaGrid()
        }
    }
}