package pt.ipbeja.estig.libraryapp

object MockData {
    private const val SAMPLE_DESCRIPTION =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud."

    val bookResources = listOf(
        Resource(1, "Os Maias", "Eça de Queirós", R.drawable.os_maias, "11.º Ano", "Romance", SAMPLE_DESCRIPTION, true),
        Resource(2, "Mensagem", "Fernando Pessoa", R.drawable.mensagem, "12.º Ano", "Poesia", SAMPLE_DESCRIPTION, false, listOf(4, 3)),
        Resource(3, "Memorial do Convento", "José Saramago", R.drawable.memorial, "12.º Ano", "Romance", SAMPLE_DESCRIPTION, true),
        Resource(4, "Os Lusíadas", "Luís de Camões", R.drawable.lusiadas, "9.º Ano", "Poesia", SAMPLE_DESCRIPTION, true)
    )

    val multimediaResources = listOf(
        Resource(5, "Inception", "Christopher Nolan", R.drawable.inception, "Geral", "Ficção Científica", SAMPLE_DESCRIPTION, true),
        Resource(6, "Interstellar", "Christopher Nolan", R.drawable.interstellar, "Geral", "Ficção Científica", SAMPLE_DESCRIPTION, false, listOf(5, 8)),
        Resource(7, "Tech Podcast", "Tech Audio", R.drawable.podcast, "Geral", "Tecnologia", SAMPLE_DESCRIPTION, true),
        Resource(8, "Nature Doc", "BBC", R.drawable.nature_doc, "Geral", "Documentário", SAMPLE_DESCRIPTION, true)
    )

    val allResources: List<Resource> = bookResources + multimediaResources

    fun resourceById(id: Int): Resource? = allResources.find { it.id == id }
}