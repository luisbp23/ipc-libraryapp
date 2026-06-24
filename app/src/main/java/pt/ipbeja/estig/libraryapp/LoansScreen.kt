package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansScreen(myLoans: MutableList<Loan>, userType: String) {
    var showRenewDialog by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Loan?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { TopAppBar(title = { Text("Os Meus Livros", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }
    ) { paddingValues ->
        if (myLoans.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ainda não requisitaste nenhum livro.", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(myLoans) { loan ->
                    LoanCard(
                        loan = loan,
                        userType = userType,
                        onRenewClick = { selectedBook = loan; showRenewDialog = true },
                        onDownloadClick = {
                            val index = myLoans.indexOf(loan)
                            myLoans[index] = myLoans[index].copy(isDownloaded = true)
                            coroutineScope.launch { snackbarHostState.showSnackbar("${loan.title} está disponível offline.") }
                        }
                    )
                }
            }
        }

        if (showRenewDialog && selectedBook != null) {
            AlertDialog(
                onDismissRequest = { showRenewDialog = false },
                title = { Text("Confirmar Renovação") },
                text = { Text("Deseja renovar '${selectedBook!!.title}'? Isto adicionará 15 dias.") },
                confirmButton = {
                    Button(onClick = {
                        val index = myLoans.indexOf(selectedBook)
                        myLoans[index] = myLoans[index].copy(daysLeft = myLoans[index].daysLeft + 15, isRenewed = true)
                        showRenewDialog = false
                    }) { Text("Confirmar") }
                },
                dismissButton = { TextButton(onClick = { showRenewDialog = false }) { Text("Cancelar") } }
            )
        }
    }
}

@Composable
fun LoanCard(loan: Loan, userType: String, onRenewClick: () -> Unit, onDownloadClick: () -> Unit) {
    val isAluno = userType == "aluno"
    val podeRenovar = !loan.isRenewed && !isAluno
    val podeDescarregar = !loan.isDownloaded && !isAluno

    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = loan.imageResId), contentDescription = "Capa", modifier = Modifier.size(60.dp, 90.dp).clip(RoundedCornerShape(6.dp)), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = loan.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = loan.author, fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))

                    val deadlineColor = if (loan.daysLeft <= 3) Color.Red else MaterialTheme.colorScheme.primary
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.WarningAmber, contentDescription = "Prazo", modifier = Modifier.size(18.dp), tint = deadlineColor)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Faltam ${loan.daysLeft} dias", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = deadlineColor)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onDownloadClick, enabled = podeDescarregar, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)) {
                    Icon(imageVector = if (loan.isDownloaded) Icons.Filled.CheckCircle else Icons.Filled.CloudDownload, contentDescription = "Offline", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isAluno) "Sem Permissão" else if (loan.isDownloaded) "Descarregado" else "Ler Offline", fontWeight = FontWeight.Bold)
                }
                Button(onClick = onRenewClick, enabled = podeRenovar, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Icon(Icons.Filled.Autorenew, contentDescription = "Renovar", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isAluno) "Sem Permissão" else if (loan.isRenewed) "Renovado" else "Renovar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}