package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun SearchScreen(onBookClick: (Resource) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredResources = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            MockData.allResources
        } else {
            MockData.allResources.filter { resource ->
                resource.title.contains(searchQuery, ignoreCase = true) ||
                        resource.author.contains(searchQuery, ignoreCase = true) ||
                        resource.category.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { SearchHeaderSection(searchQuery, onQueryChange = { searchQuery = it }) }

        // Se a caixa de pesquisa estiver vazia, mostra a secção "Que tal... ?" e depois todos os recursos
        if (searchQuery.isBlank()) {
            item { ThemesCarouselSection(onBookClick) }

            item {
                Text(
                    text = "Todos os Livros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            items(MockData.bookResources) { resource ->
                DiscoverCardItem(resource = resource, onClick = { onBookClick(resource) })
            }

            item {
                Text(
                    text = "Todas as Multimédias",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            items(MockData.multimediaResources) { resource ->
                DiscoverCardItem(resource = resource, onClick = { onBookClick(resource) })
            }
        } else {
            // Se estiver a pesquisar, mostra os resultados encontrados
            item {
                Text(
                    text = if (filteredResources.isEmpty()) "Nenhum resultado para \"$searchQuery\""
                    else "${filteredResources.size} resultado(s) para \"$searchQuery\"",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (filteredResources.isEmpty()) Color.Gray else MaterialTheme.colorScheme.onSurface
                )
            }
            items(filteredResources) { resource ->
                DiscoverCardItem(resource = resource, onClick = { onBookClick(resource) })
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchHeaderSection(query: String, onQueryChange: (String) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Pesquisar", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Título, autor ou categoria") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Ícone") },
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun ThemesCarouselSection(onBookClick: (Resource) -> Unit) {
    val showBooks by remember { mutableStateOf(Random.nextBoolean()) }
    val title = if (showBooks) "Que tal um Livro?" else "Que tal Multimédia?"
    val list = if (showBooks) MockData.bookResources else MockData.multimediaResources

    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(list) { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onBookClick(item) }) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.title,
                    modifier = Modifier.size(100.dp, 140.dp).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.title, fontSize = 11.sp, maxLines = 1)
            }
        }
    }
}

@Composable
fun DiscoverCardItem(resource: Resource, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = resource.imageResId),
                contentDescription = "Capa",
                modifier = Modifier.size(70.dp, 100.dp).clip(RoundedCornerShape(6.dp)).background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(resource.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(resource.author, color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                val preview = resource.description.take(80).let { if (resource.description.length > 80) "$it…" else it }
                Text(text = preview, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
            }
        }
    }
}