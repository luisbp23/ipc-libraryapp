package pt.ipbeja.estig.libraryapp.bibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipbeja.estig.libraryapp.CreamBeige
import pt.ipbeja.estig.libraryapp.DarkBurgundy

data class BookForm(
    val titulo: String = "",
    val autores: String = "",
    val anoPublicacao: String = "",
    val sinopse: String = "",
    val editora: String = "",
    val edicao: String = "",
    val isbn: String = "",
    val anoEscolar: String = "",
    val disciplina: String = ""
)

data class MediaForm(
    val titulo: String = "",
    val autores: String = "",
    val tipo: String = "",
    val sinopse: String = "",
    val link: String = ""
)

@Composable
fun AddResourceScreen(
    resourceType: String,
    books: List<BookForm>,
    medias: List<MediaForm>,
    onBooksChange: (List<BookForm>) -> Unit,
    onMediasChange: (List<MediaForm>) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToVerify: () -> Unit
) {
    val isLivro = resourceType == "livro"
    var currentIndex by remember { mutableStateOf(0) }
    val totalItems = if (isLivro) books.size else medias.size

    fun addNew() {
        if (isLivro) onBooksChange(books + BookForm())
        else onMediasChange(medias + MediaForm())
        currentIndex = totalItems
    }

    fun deleteCurrent() {
        if (totalItems <= 1) return
        if (isLivro) onBooksChange(books.toMutableList().also { it.removeAt(currentIndex) })
        else onMediasChange(medias.toMutableList().also { it.removeAt(currentIndex) })
        if (currentIndex >= totalItems - 1) currentIndex--
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AddResourceTopBar(
            isLivro = isLivro,
            currentIndex = currentIndex,
            total = totalItems,
            onPrevious = { if (currentIndex > 0) currentIndex-- },
            onNext = { if (currentIndex < totalItems - 1) currentIndex++ },
            onAdd = { addNew() },
            onDelete = { deleteCurrent() },
            onNavigateBack = onNavigateBack
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (isLivro) {
                val current = books[currentIndex]
                BookFormContent(
                    form = current,
                    onFormChange = { updated ->
                        onBooksChange(books.toMutableList().also { list -> list[currentIndex] = updated })
                    }
                )
            } else {
                val current = medias[currentIndex]
                MediaFormContent(
                    form = current,
                    onFormChange = { updated ->
                        onMediasChange(medias.toMutableList().also { list -> list[currentIndex] = updated })
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onNavigateToVerify() },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBurgundy)
            ) {
                Text("Finalizar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = CreamBeige)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AddResourceTopBar(
    isLivro: Boolean,
    currentIndex: Int,
    total: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(Color(0xFFCD0402), CreamBeige)))
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar",
                        tint = DarkBurgundy, modifier = Modifier.size(32.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        if (isLivro) Icons.Filled.Book else Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = DarkBurgundy,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = if (isLivro) "Adicionar Novo Livro" else "Adicionar Multimédia",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DarkBurgundy
                    )
                }
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (total > 1) {
                    IconButton(onClick = onDelete, modifier = Modifier.size(40.dp)) {
                        Icon(Icons.Filled.Delete, contentDescription = "Apagar",
                            tint = DarkBurgundy, modifier = Modifier.size(28.dp))
                    }
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = onPrevious,
                        enabled = currentIndex > 0,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Anterior",
                            tint = if (currentIndex > 0) DarkBurgundy else Color.Gray,
                            modifier = Modifier.size(28.dp))
                    }
                    Text(
                        text = "${currentIndex + 1} / $total",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DarkBurgundy
                    )
                    IconButton(
                        onClick = onNext,
                        enabled = currentIndex < total - 1,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "Seguinte",
                            tint = if (currentIndex < total - 1) DarkBurgundy else Color.Gray,
                            modifier = Modifier.size(28.dp))
                    }
                }

                IconButton(onClick = onAdd, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar",
                        tint = DarkBurgundy, modifier = Modifier.size(28.dp))
                }
            }
        }
    }
}

@Composable
fun BookFormContent(form: BookForm, onFormChange: (BookForm) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoverPicker(modifier = Modifier.width(120.dp).height(160.dp))

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FormField(label = "Título", value = form.titulo,
                onValueChange = { onFormChange(form.copy(titulo = it)) })
            FormField(label = "Autor(es)", value = form.autores,
                onValueChange = { onFormChange(form.copy(autores = it)) })
        }
    }

    FormField(
        label = "Ano de Publicação",
        value = form.anoPublicacao,
        onValueChange = { onFormChange(form.copy(anoPublicacao = it)) }
    )

    SectionLabel("Detalhes")

    FormField(label = "Sinopse", value = form.sinopse,
        onValueChange = { onFormChange(form.copy(sinopse = it)) }, minLines = 4)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FormField(label = "Editora", value = form.editora,
            onValueChange = { onFormChange(form.copy(editora = it)) },
            modifier = Modifier.weight(1f))
        FormField(label = "Edição", value = form.edicao,
            onValueChange = { onFormChange(form.copy(edicao = it)) },
            modifier = Modifier.weight(1f))
    }

    FormField(label = "ISBN", value = form.isbn,
        onValueChange = { onFormChange(form.copy(isbn = it)) })

    SectionLabel("Classificação")

    AnoEscolarDropdown(selected = form.anoEscolar,
        onSelected = { onFormChange(form.copy(anoEscolar = it)) })
    DisciplinaDropdown(selected = form.disciplina,
        onSelected = { onFormChange(form.copy(disciplina = it)) })
}

@Composable
fun MediaFormContent(form: MediaForm, onFormChange: (MediaForm) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoverPicker(modifier = Modifier.width(120.dp).height(160.dp))

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FormField(label = "Título", value = form.titulo,
                onValueChange = { onFormChange(form.copy(titulo = it)) })
            FormField(label = "Autor(es)", value = form.autores,
                onValueChange = { onFormChange(form.copy(autores = it)) })
            TipoMediaDropdown(selected = form.tipo,
                onSelected = { onFormChange(form.copy(tipo = it)) })
        }
    }

    SectionLabel("Detalhes")

    FormField(label = "Sinopse", value = form.sinopse,
        onValueChange = { onFormChange(form.copy(sinopse = it)) }, minLines = 4)

    FormField(label = "Link / Ficheiro", value = form.link,
        onValueChange = { onFormChange(form.copy(link = it)) })
}

@Composable
fun CoverPicker(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF9E9E9E))
            .border(1.dp, DarkBurgundy, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.AddPhotoAlternate, contentDescription = "Adicionar Capa",
                tint = DarkBurgundy, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text("Adicionar\nCapa", fontSize = 16.sp, color = DarkBurgundy,
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    minLines: Int = 1
) {
    Column(modifier = modifier) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = DarkBurgundy)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            minLines = minLines,
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF5E6C8),
                focusedContainerColor = Color(0xFFF5E6C8),
                unfocusedBorderColor = Color(0xFFBBA980),
                focusedBorderColor = DarkBurgundy
            )
        )
    }
}

@Composable
fun SectionLabel(text: String) {
    Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkBurgundy,
        modifier = Modifier.padding(top = 4.dp))
    HorizontalDivider(color = DarkBurgundy, thickness = 1.dp)
}

@Composable
fun TipoMediaDropdown(selected: String, onSelected: (String) -> Unit) {
    val tipos = listOf("Filme", "Podcast", "Audiobook", "Documentário", "Série", "Outro")
    FormDropdown(label = "Tipo", selected = selected, options = tipos, onSelected = onSelected)
}

@Composable
fun AnoEscolarDropdown(selected: String, onSelected: (String) -> Unit) {
    val anos = (1..9).map { "${it}º Ano" }
    FormDropdown(label = "Ano Escolar", selected = selected, options = anos, onSelected = onSelected)
}

@Composable
fun DisciplinaDropdown(selected: String, onSelected: (String) -> Unit) {
    val disciplinas = listOf(
        "Português", "Matemática", "Inglês", "Francês",
        "História", "Geografia", "Ciências Naturais",
        "Físico-Química", "Educação Visual", "Educação Tecnológica",
        "Educação Física", "EMRC", "TIC"
    )
    FormDropdown(label = "Disciplina", selected = selected, options = disciplinas, onSelected = onSelected)
}

@Composable
fun FormDropdown(label: String, selected: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = DarkBurgundy)
        Spacer(modifier = Modifier.height(2.dp))
        Box {
            OutlinedTextField(
                value = selected.ifEmpty { "Selecionar $label" },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null,
                            modifier = Modifier.size(28.dp))
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF5E6C8),
                    focusedContainerColor = Color(0xFFF5E6C8),
                    unfocusedBorderColor = Color(0xFFBBA980),
                    focusedBorderColor = DarkBurgundy
                )
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 18.sp) },
                        onClick = { onSelected(option); expanded = false }
                    )
                }
            }
        }
    }
}