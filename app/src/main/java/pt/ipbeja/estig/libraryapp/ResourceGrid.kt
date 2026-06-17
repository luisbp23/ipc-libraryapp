/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Data class representing a catalog resource with a local image resource ID.
 */
data class Resource(val id: Int, val title: String, val author: String, val imageResId: Int)

/**
 * Grid component displaying the list of book resources.
 */
@Composable
fun ResourcesGrid() {
    val resources = listOf(
        Resource(1, "Os Maias", "Eça de Queirós", R.drawable.os_maias),
        Resource(2, "Mensagem", "Fernando Pessoa", R.drawable.mensagem),
        Resource(3, "Memorial do Convento", "José Saramago", R.drawable.memorial),
        Resource(4, "Os Lusíadas", "Luís de Camões", R.drawable.lusiadas)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(resources) { resource -> ResourceCard(resource) }
    }
}

/**
 * Grid component displaying the list of multimedia resources.
 */
@Composable
fun MultimediaGrid() {
    val multimedia = listOf(
        Resource(5, "Inception", "Christopher Nolan", R.drawable.inception),
        Resource(6, "Interstellar", "Christopher Nolan", R.drawable.interstellar),
        Resource(7, "Tech Podcast", "Tech Audio", R.drawable.podcast),
        Resource(8, "Nature Doc", "BBC", R.drawable.nature_doc)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(multimedia) { resource -> ResourceCard(resource) }
    }
}

/**
 * Visual card representing a single resource with a local cover.
 */
@Composable
fun ResourceCard(resource: Resource) {
    Card(
        modifier = Modifier.fillMaxWidth().height(260.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResourceCoverImage(resource.imageResId, resource.title)
            Spacer(modifier = Modifier.height(12.dp))
            ResourceTextInfo(resource)
        }
    }
}

@Composable
fun ColumnScope.ResourceCoverImage(imageResId: Int, title: String) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Cover of $title",
        modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray.copy(alpha = 0.2f)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ResourceTextInfo(resource: Resource) {
    Text(text = resource.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, textAlign = TextAlign.Center, maxLines = 1)
    Text(text = resource.author, fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center, maxLines = 1)
    Spacer(modifier = Modifier.height(4.dp))
}