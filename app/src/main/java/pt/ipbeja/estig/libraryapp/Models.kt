package pt.ipbeja.estig.libraryapp

// Modelo de Recurso unificado
data class Resource(
    val id: Int,
    val title: String,
    val author: String,
    val imageResId: Int,
    val year: String,
    val category: String,
    val description: String,
    val available: Boolean,
    val alternativeIds: List<Int> = emptyList()
)

// Modelo de Empréstimo unificado (Junta a lógica de dias do Luís com a tua lógica de downloads)
data class Loan(
    val id: Int,
    val title: String,
    val author: String,
    val imageResId: Int,
    var daysLeft: Int,
    var isRenewed: Boolean = false,
    var isDownloaded: Boolean = false
)