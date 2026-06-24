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
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = resource.imageResId),
                contentDescription = "Capa",
                modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = resource.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, textAlign = TextAlign.Center, maxLines = 1)
            Text(text = resource.author, fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}