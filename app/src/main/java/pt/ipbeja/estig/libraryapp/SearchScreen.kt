/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
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
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { SearchHeaderSection(searchQuery, onQueryChange = { searchQuery = it }) }
        item { ThemesCarouselSection() }
        item { DiscoverTitleSection() }
        items(4) { DiscoverCardItem() }
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
        placeholder = { Text("Título, autor ou ISBN") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Ícone de Pesquisa") },
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun ThemesCarouselSection() {
    // Lista de temas possíveis
    val temas = listOf(
        "Ficção", "Aventura", "Ciência", "História",
        "Romance", "Poesia", "Mistério", "Fantasia", "Terror"
    )

    // Escolhe um tema aleatório sempre que o ecrã é aberto
    val temaAleatorio = remember { temas.random() }

    // Mostra o tema sorteado
    Text(text = "Que tal $temaAleatorio?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))

    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(5) {
            Box(
                modifier = Modifier
                    .size(100.dp, 140.dp)
                    .background(Color.DarkGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Book, contentDescription = null, tint = Color.LightGray)
            }
        }
    }
}

@Composable
fun DiscoverTitleSection() {
    Text(text = "Descobrir", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DiscoverCardItem() {
    Card(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp, 100.dp)
                    .background(Color.Gray, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Book, contentDescription = null, tint = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Livro Recomendado", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Nome do Autor", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Uma breve descrição ou sinopse do livro aparece aqui...",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}