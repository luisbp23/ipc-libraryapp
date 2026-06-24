package pt.ipbeja.estig.libraryapp.bibliotecario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pt.ipbeja.estig.libraryapp.CreamBeige
import pt.ipbeja.estig.libraryapp.DarkBurgundy
import pt.ipbeja.estig.libraryapp.DarkGray
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen() {
    val activeLoansPlaceholder = remember {
        listOf(
            Triple("The U-Bike System", "João Silva (Aluno - Nº 6405)", 3),
            Triple("Computer Networks", "Prof. Ricardo Guilherme", 12)
        )
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFCD0402),
                                CreamBeige
                            )
                        )
                    )
                    .statusBarsPadding()
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Livros Requisitados",
                            fontWeight = FontWeight.Bold,
                            color = DarkBurgundy
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Requisições ativas:",
                    color = DarkGray,
                    fontSize = 14.sp
                )
            }

            items(activeLoansPlaceholder) { loan ->
                AdminLoanCard(
                    title = loan.first,
                    borrower = loan.second,
                    daysLeft = loan.third
                )
            }
        }
    }
}

@Composable
fun AdminLoanCard(title: String, borrower: String, daysLeft: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(55.dp, 80.dp)
                    .background(DarkBurgundy, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Book,
                    contentDescription = null,
                    tint = CreamBeige
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Por: $borrower",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val deadlineColor = if (daysLeft <= 3) Color.Red else DarkBurgundy
                    Icon(
                        Icons.Filled.WarningAmber,
                        contentDescription = "Prazo",
                        modifier = Modifier.size(16.dp),
                        tint = deadlineColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (daysLeft == 1) "Falta 1 dia" else "Faltam $daysLeft dias",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = deadlineColor
                    )
                }
            }
        }
    }
}