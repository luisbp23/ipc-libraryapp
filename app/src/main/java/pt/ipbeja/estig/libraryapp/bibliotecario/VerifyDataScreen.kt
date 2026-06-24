package pt.ipbeja.estig.libraryapp.bibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipbeja.estig.libraryapp.CreamBeige
import pt.ipbeja.estig.libraryapp.DarkBurgundy

@Composable
fun VerifyDataScreen(
    books: List<BookForm>,
    medias: List<MediaForm>,
    resourceType: String,
    onBack: () -> Unit,
    onValidate: () -> Unit
) {
    val isLivro = resourceType == "livro"
    val total = if (isLivro) books.size else medias.size
    var currentIndex by remember { mutableStateOf(0) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {},
            containerColor = CreamBeige,
            titleContentColor = DarkBurgundy,
            textContentColor = DarkBurgundy,
            text = {
                Text(
                    "Recurso(s) adicionados com sucesso.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            confirmButton = {
                TextButton(onClick = onValidate) {
                    Text("Ok", fontSize = 18.sp, color = DarkBurgundy)
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFFCD0402), CreamBeige)))
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { if (currentIndex > 0) currentIndex-- },
                    enabled = currentIndex > 0
                ) {
                    Text("←", fontSize = 28.sp,
                        color = if (currentIndex > 0) DarkBurgundy else Color.Gray,
                        fontWeight = FontWeight.Bold)
                }
                Text(
                    text = "Verificar dados (${currentIndex + 1}/$total)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkBurgundy
                )
                IconButton(
                    onClick = { if (currentIndex < total - 1) currentIndex++ },
                    enabled = currentIndex < total - 1
                ) {
                    Text("→", fontSize = 28.sp,
                        color = if (currentIndex < total - 1) DarkBurgundy else Color.Gray,
                        fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isLivro) {
                VerifyBookContent(book = books[currentIndex])
            } else {
                VerifyMediaContent(media = medias[currentIndex])
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f).height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF393939))
            ) {
                Text("Voltar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Button(
                onClick = { showSuccessDialog = true },
                modifier = Modifier.weight(1f).height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Validar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun VerifyBookContent(book: BookForm) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF9E9E9E), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("Capa", color = DarkBurgundy, fontWeight = FontWeight.Bold, fontSize = 22.sp)
    }

    VerifyField(label = "Título", value = book.titulo)
    VerifyField(label = "Autor(es)", value = book.autores)
    VerifyField(label = "Ano de Publicação", value = book.anoPublicacao)

    SectionLabel("Detalhes")
    VerifyField(label = "Sinopse", value = book.sinopse)
    VerifyField(label = "Editora", value = book.editora)
    VerifyField(label = "Edição", value = book.edicao)
    VerifyField(label = "ISBN", value = book.isbn)

    SectionLabel("Classificação")
    VerifyField(label = "Ano Escolar", value = book.anoEscolar)
    VerifyField(label = "Disciplina", value = book.disciplina)
}

@Composable
fun VerifyMediaContent(media: MediaForm) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF9E9E9E), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("Capa", color = DarkBurgundy, fontWeight = FontWeight.Bold, fontSize = 22.sp)
    }

    VerifyField(label = "Título", value = media.titulo)
    VerifyField(label = "Autor(es)", value = media.autores)
    VerifyField(label = "Tipo", value = media.tipo)

    SectionLabel("Detalhes")
    VerifyField(label = "Sinopse", value = media.sinopse)
    VerifyField(label = "Link / Ficheiro", value = media.link)
}

@Composable
fun VerifyField(label: String, value: String) {
    Column {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = DarkBurgundy)
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5E6C8), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Text(
                text = value.ifEmpty { "—" },
                fontSize = 18.sp,
                color = if (value.isEmpty()) Color.Gray else Color(0xFF393939)
            )
        }
    }
}