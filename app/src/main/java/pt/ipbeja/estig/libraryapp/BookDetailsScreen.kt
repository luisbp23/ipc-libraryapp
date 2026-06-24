/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val AvailableGreen = Color(0xFF2E7D32)
private val UnavailableRed = Color(0xFFC62828)

/**
 * Detailed view of a single resource. Shows metadata, current availability
 * and either a "Requisitar" action (available) or a list of alternatives
 * (unavailable).
 */
@Composable
fun BookDetailsScreen(
    resource: Resource,
    onBack: () -> Unit,
    onAlternativeClick: (Resource) -> Unit
) {
    var showSuccessDialog by remember(resource.id) { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
        }

        BookDetailsCard(resource)

        Spacer(modifier = Modifier.height(16.dp))

        if (resource.available) {
            RequisitarButton(onClick = { showSuccessDialog = true })
        } else {
            AlternativesSection(
                alternativeIds = resource.alternativeIds,
                onAlternativeClick = onAlternativeClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showSuccessDialog) {
        RequestSuccessDialog(onDismiss = { showSuccessDialog = false })
    }
}

@Composable
private fun BookDetailsCard(resource: Resource) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = resource.imageResId),
            contentDescription = "Capa de ${resource.title}",
            modifier = Modifier
                .size(width = 140.dp, height = 220.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray.copy(alpha = 0.2f)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = resource.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            MetadataLine(label = "Autor", value = resource.author)
            MetadataLine(label = "Ano Escolar", value = resource.year)
            MetadataLine(label = "Categoria", value = resource.category)

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Descrição:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = resource.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            StatusLine(available = resource.available)
        }
    }
}

@Composable
private fun MetadataLine(label: String, value: String) {
    Row {
        Text(text = "$label: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = value, fontSize = 14.sp)
    }
}

@Composable
private fun StatusLine(available: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Estado: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(
            text = if (available) "Disponível" else "Indisponível",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = if (available) AvailableGreen else UnavailableRed
        )
    }
}

@Composable
private fun RequisitarButton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(56.dp)
        ) {
            Text(
                text = "REQUISITAR",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun AlternativesSection(
    alternativeIds: List<Int>,
    onAlternativeClick: (Resource) -> Unit
) {
    val alternatives = alternativeIds.mapNotNull { resourceById(it) }
    if (alternatives.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vê as nossas alternativas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ExpandMore, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        alternatives.forEach { alt ->
            AlternativeCard(resource = alt, onClick = { onAlternativeClick(alt) })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AlternativeCard(resource: Resource, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(id = resource.imageResId),
                contentDescription = "Capa de ${resource.title}",
                modifier = Modifier
                    .size(width = 70.dp, height = 110.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.DarkGray.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = resource.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(6.dp))
                MetadataLine(label = "Autor", value = resource.author)
                MetadataLine(label = "Ano Escolar", value = resource.year)
                MetadataLine(label = "Categoria", value = resource.category)
            }
        }
    }
}

@Composable
private fun RequestSuccessDialog(onDismiss: () -> Unit) {
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-PT")) }
    val calendar = remember { Calendar.getInstance() }
    val startDate = remember { formatter.format(calendar.time) }
    val returnDate = remember {
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        formatter.format(calendar.time)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Requisição realizada com sucesso!",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column {
                Text(text = "O livro está pronto para levantamento.")
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Data de início: $startDate", fontWeight = FontWeight.SemiBold)
                Text(text = "Data de devolução: $returnDate", fontWeight = FontWeight.SemiBold)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK", fontWeight = FontWeight.Bold)
            }
        }
    )
}

