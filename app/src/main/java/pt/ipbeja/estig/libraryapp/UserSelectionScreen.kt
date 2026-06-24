/**
 * Author: Luís Pegas
 * Student Number: 26873
 */

package pt.ipbeja.estig.libraryapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserSelectionScreen(onUserSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LibraryApp",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DeepRed
        )
        Text(
            text = "Iniciar sessão como:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        UserTypeCard(
            icon = Icons.Filled.Person,
            label = "Aluno",
            description = "Explorar e requisitar recursos",
            onClick = { onUserSelected("aluno") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserTypeCard(
            icon = Icons.Filled.School,
            label = "Professor",
            description = "Requisitar e gerir os meus livros",
            onClick = { onUserSelected("professor") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserTypeCard(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            label = "Bibliotecário",
            description = "Gerir recursos",
            onClick = { onUserSelected("admin") }
        )
    }
}

@Composable
fun UserTypeCard(
    icon: ImageVector,
    label: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = DeepRed,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DarkGray)
                Text(description, fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserTypeCardPreview() {
    LibraryAppTheme {
        UserTypeCard(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            label = "Bibliotecário",
            description = "Gerir recursos",
            onClick = {}
        )
    }
}