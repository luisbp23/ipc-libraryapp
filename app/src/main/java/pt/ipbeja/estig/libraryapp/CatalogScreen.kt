package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CatalogScreen(onBookClick: (Resource) -> Unit = {}, onGoHome: () -> Unit = {}) {
    CatalogContent(Modifier, onBookClick, onGoHome)
}

@Composable
fun CatalogContent(modifier: Modifier = Modifier, onBookClick: (Resource) -> Unit = {}, onGoHome: () -> Unit = {}) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedAno by remember { mutableStateOf("Ano Esc.") }
    var selectedTema by remember { mutableStateOf("Tema") }
    var selectedFiltro by remember { mutableStateOf("Filtros") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        TopHeader(onUserIconClick = onGoHome)
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(8.dp))
        TabsSection(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { newIndex ->
                selectedTabIndex = newIndex
                selectedAno = "Ano Esc."
                selectedTema = "Tema"
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        FiltersSection(
            selectedTabIndex = selectedTabIndex,
            selectedAno = selectedAno,
            onAnoSelected = { selectedAno = it },
            selectedTema = selectedTema,
            onTemaSelected = { selectedTema = it },
            selectedFiltro = selectedFiltro,
            onFiltroSelected = { selectedFiltro = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // LÓGICA DE FILTRAGEM
        var baseList = if (selectedTabIndex == 0) MockData.bookResources else MockData.multimediaResources

        if (selectedTabIndex == 0 && selectedAno != "Ano Esc." && selectedAno != "Qualquer Ano Esc.") {
            baseList = baseList.filter { it.year == selectedAno }
        }
        if (selectedTabIndex == 1 && selectedTema != "Tema" && selectedTema != "Qualquer Tema") {
            baseList = baseList.filter { it.category == selectedTema }
        }

        // LÓGICA DE ORDENAÇÃO
        if (selectedFiltro == "A-Z") {
            baseList = baseList.sortedBy { it.title }
        } else if (selectedFiltro == "Mais Recentes") {
            baseList = baseList.sortedByDescending { it.id }
        }

        ResourceGrid(resources = baseList, onResourceClick = onBookClick)
    }
}