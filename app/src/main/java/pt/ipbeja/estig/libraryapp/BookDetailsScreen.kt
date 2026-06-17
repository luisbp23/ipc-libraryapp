/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookDetailsScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        BookCoverSection()
        Spacer(modifier = Modifier.height(24.dp))
        BookTitleSection()
        Spacer(modifier = Modifier.height(16.dp))
        BookStatsSection()
        Spacer(modifier = Modifier.height(24.dp))
        BookActionsSection()
        Spacer(modifier = Modifier.height(32.dp))
        BookSynopsisSection()
        Spacer(modifier = Modifier.height(32.dp))
        BookRelatedSection()
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun BookCoverSection() {
    Box(modifier = Modifier.size(180.dp, 260.dp).background(Color.DarkGray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
        Icon(Icons.Filled.Book, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(64.dp))
    }
}

@Composable
fun BookTitleSection() {
    Text(text = "The Book Title Here", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    Text(text = "Author Name", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
}

@Composable
fun BookStatsSection() {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Filled.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("4.5", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(16.dp))
        Text("•", color = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text("240 Pages", color = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text("•", color = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text("Fiction", color = Color.Gray)
    }
}

@Composable
fun BookActionsSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(onClick = { }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Text("Request", fontWeight = FontWeight.Bold)
        }
        OutlinedButton(onClick = { }, modifier = Modifier.weight(1f)) {
            Text("Read Sample", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BookSynopsisSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Synopsis", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "This is a detailed description of the book. It provides an overview of the plot, the main characters, and the setting.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 20.sp)
    }
}

@Composable
fun BookRelatedSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Related Books", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(4) {
                Box(modifier = Modifier.size(90.dp, 130.dp).background(Color.Gray, RoundedCornerShape(6.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Book, contentDescription = null, tint = Color.LightGray)
                }
            }
        }
    }
}
