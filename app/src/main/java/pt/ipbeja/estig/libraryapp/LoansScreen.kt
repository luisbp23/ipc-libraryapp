/**
 * Author: Ricardo Dias Guilherme
 * Student Number: 26971
 */
package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class Loan(
    val id: Int, val title: String, val author: String, var daysLeft: Int, var isRenewed: Boolean = false, var isDownloaded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansScreen() {
    val loans = remember {
        mutableStateListOf(Loan(1, "The U-Bike System", "Ricardo Guilherme", 3), Loan(2, "Computer Networks", "Andrew S. Tanenbaum", 12))
    }
    var showRenewDialog by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Loan?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { TopAppBar(title = { Text("My Library", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }
    ) { paddingValues ->
        LoansList(
            modifier = Modifier.padding(paddingValues), loans = loans,
            onRenewRequested = { loan -> selectedBook = loan; showRenewDialog = true },
            onDownloadRequested = { loan ->
                val index = loans.indexOf(loan)
                if (index != -1) {
                    loans[index] = loans[index].copy(isDownloaded = true)
                    coroutineScope.launch { snackbarHostState.showSnackbar("${loan.title} is now available offline.") }
                }
            }
        )

        if (showRenewDialog && selectedBook != null) {
            RenewConfirmationDialog(
                bookTitle = selectedBook!!.title,
                onConfirm = {
                    val index = loans.indexOf(selectedBook)
                    if (index != -1) loans[index] = loans[index].copy(daysLeft = loans[index].daysLeft + 15, isRenewed = true)
                    showRenewDialog = false
                },
                onDismiss = { showRenewDialog = false }
            )
        }
    }
}

@Composable
fun LoansList(modifier: Modifier = Modifier, loans: List<Loan>, onRenewRequested: (Loan) -> Unit, onDownloadRequested: (Loan) -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { Spacer(modifier = Modifier.height(4.dp)); Text(text = "Manage your borrowed books:", color = Color.Gray, fontSize = 14.sp) }
        items(loans) { loan -> LoanCard(loan = loan, onRenewClick = { onRenewRequested(loan) }, onDownloadClick = { onDownloadRequested(loan) }) }
    }
}

@Composable
fun RenewConfirmationDialog(bookTitle: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Renewal") },
        text = { Text("Do you want to renew '$bookTitle'? This will add 15 days to your deadline.") },
        confirmButton = { Button(onClick = onConfirm) { Text("Confirm") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Gray) } }
    )
}

@Composable
fun LoanCard(loan: Loan, onRenewClick: () -> Unit, onDownloadClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LoanDetailsSection(loan)
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))
            LoanActionButtons(loan, onRenewClick, onDownloadClick)
        }
    }
}

@Composable
fun LoanDetailsSection(loan: Loan) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(60.dp, 90.dp).background(Color.DarkGray, RoundedCornerShape(6.dp)), contentAlignment = Alignment.Center) { Icon(Icons.Filled.Book, contentDescription = null, tint = Color.LightGray) }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = loan.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = loan.author, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            LoanDeadlineIndicator(loan.daysLeft)
        }
    }
}

@Composable
fun LoanDeadlineIndicator(daysLeft: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val deadlineColor = if (daysLeft <= 3) Color.Red else MaterialTheme.colorScheme.primary
        Icon(Icons.Filled.WarningAmber, contentDescription = "Deadline", modifier = Modifier.size(18.dp), tint = deadlineColor)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = if (daysLeft == 1) "1 day left" else "$daysLeft days left", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = deadlineColor)
    }
}

@Composable
fun LoanActionButtons(loan: Loan, onRenewClick: () -> Unit, onDownloadClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(onClick = onDownloadClick, enabled = !loan.isDownloaded, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)) {
            Icon(imageVector = if (loan.isDownloaded) Icons.Filled.CheckCircle else Icons.Filled.CloudDownload, contentDescription = "Read Offline", modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (loan.isDownloaded) "Downloaded" else "Read Offline", fontWeight = FontWeight.Bold)
        }
        Button(onClick = onRenewClick, enabled = !loan.isRenewed, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Icon(Icons.Filled.Autorenew, contentDescription = "Renew", modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (loan.isRenewed) "Renewed" else "Renew", fontWeight = FontWeight.Bold)
        }
    }
}