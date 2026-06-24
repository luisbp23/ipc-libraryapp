/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.activity.compose.BackHandler
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
    var selectedResource by remember { mutableStateOf<Resource?>(null) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    if (selectedResource != null) {
        BackHandler(onBack = { selectedResource = null })
    }

    Column(
        modifier = Modifier
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

        if (selectedResource != null) {
            BookDetailsScreen(
                resource = selectedResource!!,
                onBack = { selectedResource = null },
                onAlternativeClick = { selectedResource = it }
            )
        } else {
            val resources = if (selectedTabIndex == 0) bookResources else multimediaResources
            ResourceGrid(resources = resources, onResourceClick = { selectedResource = it })
        }
    }
}
