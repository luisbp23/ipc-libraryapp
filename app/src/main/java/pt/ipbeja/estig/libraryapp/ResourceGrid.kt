/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
data class Resource(
    val id: Int,
    val title: String,
    val author: String,
    val imageResId: Int,
    val year: String,
    val category: String,
    val description: String,
    val available: Boolean,
    val alternativeIds: List<Int> = emptyList()
)

private const val SAMPLE_DESCRIPTION =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor " +
    "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud."

val bookResources = listOf(
    Resource(
        id = 1,
        title = "Os Maias",
        author = "Eça de Queirós",
        imageResId = R.drawable.os_maias,
        year = "11.º Ano",
        category = "Romance",
        description = SAMPLE_DESCRIPTION,
        available = true
    ),
    Resource(
        id = 2,
        title = "Mensagem",
        author = "Fernando Pessoa",
        imageResId = R.drawable.mensagem,
        year = "12.º Ano",
        category = "Poesia",
        description = SAMPLE_DESCRIPTION,
        available = false,
        alternativeIds = listOf(4, 3)
    ),
    Resource(
        id = 3,
        title = "Memorial do Convento",
        author = "José Saramago",
        imageResId = R.drawable.memorial,
        year = "12.º Ano",
        category = "Romance",
        description = SAMPLE_DESCRIPTION,
        available = true
    ),
    Resource(
        id = 4,
        title = "Os Lusíadas",
        author = "Luís de Camões",
        imageResId = R.drawable.lusiadas,
        year = "9.º Ano",
        category = "Poesia",
        description = SAMPLE_DESCRIPTION,
        available = true
    )
)

val multimediaResources = listOf(
    Resource(
        id = 5,
        title = "Inception",
        author = "Christopher Nolan",
        imageResId = R.drawable.inception,
        year = "—",
        category = "Ficção Científica",
        description = SAMPLE_DESCRIPTION,
        available = true
    ),
    Resource(
        id = 6,
        title = "Interstellar",
        author = "Christopher Nolan",
        imageResId = R.drawable.interstellar,
        year = "—",
        category = "Ficção Científica",
        description = SAMPLE_DESCRIPTION,
        available = false,
        alternativeIds = listOf(5, 8)
    ),
    Resource(
        id = 7,
        title = "Tech Podcast",
        author = "Tech Audio",
        imageResId = R.drawable.podcast,
        year = "—",
        category = "Tecnologia",
        description = SAMPLE_DESCRIPTION,
        available = true
    ),
    Resource(
        id = 8,
        title = "Nature Doc",
        author = "BBC",
        imageResId = R.drawable.nature_doc,
        year = "—",
        category = "Documentário",
        description = SAMPLE_DESCRIPTION,
        available = true
    )
)

val allResources: List<Resource> = bookResources + multimediaResources

fun resourceById(id: Int): Resource? = allResources.find { it.id == id }

/**
 * Grid component displaying a list of resources (books or multimedia).
 */
@Composable
fun ResourceGrid(resources: List<Resource>, onResourceClick: (Resource) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(resources) { resource -> ResourceCard(resource, onClick = { onResourceClick(resource) }) }
    }
}

/**
 * Visual card representing a single resource with a local cover.
 */
@Composable
fun ResourceCard(resource: Resource, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable(onClick = onClick),
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
