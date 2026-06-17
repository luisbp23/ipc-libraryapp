/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Main catalog screen displaying the available resources.
 */
@Composable
fun CatalogScreen() {
    Scaffold { paddingValues ->
        CatalogContent(Modifier.padding(paddingValues))
    }
}

/**
 * Content layout for the catalog screen.
 *
 * @param modifier The modifier to be applied to the layout.
 */
@Composable
fun CatalogContent(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
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