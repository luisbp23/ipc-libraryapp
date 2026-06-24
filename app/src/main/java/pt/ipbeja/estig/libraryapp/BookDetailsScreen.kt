package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import pt.ipbeja.estig.libraryapp.MockData

private val AvailableGreen = Color(0xFF2E7D32)
private val UnavailableRed = Color(0xFFC62828)

@Composable
fun BookDetailsScreen(
    resource: Resource,
    userType: String = "",
    onBack: () -> Unit,
    onAlternativeClick: (Resource) -> Unit,
    onRequisitar: (Resource) -> Unit = {},
    onDownload: (Resource) -> Unit = {},
    myLoans: List<Loan> = emptyList()
) {
    var showSuccessDialog by remember(resource.id) { mutableStateOf(false) }
    var showDlDialog  by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val existingLoan: Loan? = myLoans.find { loan -> loan.id == resource.id }
    val jaRequisitado  = existingLoan != null
    val jaDescarregado = existingLoan?.isDownloaded == true
    val isAluno = userType == "aluno"

    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {

        // 1. CABEÇALHO FIXO NO TOPO COM TÍTULO DINÂMICO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
            }

            // Lógica para alterar o texto com base no tipo de recurso
            val isMultimedia = MockData.multimediaResources.any { it.id == resource.id }
            val tituloDetalhes = if (isMultimedia) {
                "Detalhes Multimedia - ${resource.title}"
            } else {
                "Detalhes do Livro - ${resource.title}"
            }

            Text(
                text = tituloDetalhes,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 2. CAIXA CENTRALIZADORA (Garante que a informação não cola ao topo)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {

                // Capa e Informações
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    Image(
                        painter = painterResource(id = resource.imageResId),
                        contentDescription = "Capa",
                        modifier = Modifier
                            .size(140.dp, 220.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray.copy(alpha = 0.2f)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = resource.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(12.dp))
                        MetadataLine(label = "Autor", value = resource.author)
                        MetadataLine(label = "Ano Escolar", value = resource.year)
                        MetadataLine(label = "Categoria", value = resource.category)

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Descrição:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = resource.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 18.sp)

                        Spacer(modifier = Modifier.height(12.dp))
                        StatusLine(available = resource.available)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botões de Ação
                if (resource.available) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { onRequisitar(resource); showSuccessDialog = true },
                            enabled = !jaRequisitado,
                            modifier = Modifier.weight(1f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if (jaRequisitado) Color.Gray else MaterialTheme.colorScheme.primary)
                        ) { Text(if (jaRequisitado) "Requisitado" else "Requisitar", fontWeight = FontWeight.Bold) }

                        val podeDescarregar = jaRequisitado && !jaDescarregado && !isAluno
                        Button(
                            onClick = { onDownload(resource); showDlDialog = true },
                            enabled = podeDescarregar,
                            modifier = Modifier.weight(1f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if (!podeDescarregar) Color.Gray else MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = if (isAluno) "Sem Permissão" else if (jaDescarregado) "Descarregado" else "Download",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    AlternativesSection(alternativeIds = resource.alternativeIds, onAlternativeClick = onAlternativeClick)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    // Diálogos de Sucesso
    if (showSuccessDialog) RequestSuccessDialog(onDismiss = { showSuccessDialog = false })
    if (showDlDialog) {
        AlertDialog(
            onDismissRequest = { showDlDialog = false },
            title = { Text("Download Concluído") },
            text  = { Text("O recurso '${resource.title}' está disponível offline.") },
            confirmButton = { Button(onClick = { showDlDialog = false }) { Text("OK") } }
        )
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
        Text(text = if (available) "Disponível" else "Indisponível", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = if (available) AvailableGreen else UnavailableRed)
    }
}

@Composable
private fun AlternativesSection(alternativeIds: List<Int>, onAlternativeClick: (Resource) -> Unit) {
    val alternativas = alternativeIds.mapNotNull { MockData.resourceById(it) }
    if (alternativas.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Vê as nossas alternativas", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Icon(Icons.Filled.ExpandMore, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(alternativas) { alt ->
                Card(modifier = Modifier.width(240.dp).clickable { onAlternativeClick(alt) }) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Image(
                            painter = painterResource(id = alt.imageResId),
                            contentDescription = "Capa",
                            modifier = Modifier.size(60.dp, 90.dp).clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = alt.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                            MetadataLine(label = "Autor", value = alt.author)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RequestSuccessDialog(onDismiss: () -> Unit) {
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-PT")) }
    val calendar = remember { Calendar.getInstance() }
    val startDate = remember { formatter.format(calendar.time) }
    val returnDate = remember { calendar.add(Calendar.DAY_OF_MONTH, 15); formatter.format(calendar.time) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Requisição efetuada!", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(text = "O recurso foi adicionado aos teus empréstimos.")
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Início: $startDate", fontWeight = FontWeight.SemiBold)
                Text(text = "Devolução: $returnDate", fontWeight = FontWeight.SemiBold)
            }
        },
        confirmButton = { Button(onClick = onDismiss) { Text("OK") } }
    )
}