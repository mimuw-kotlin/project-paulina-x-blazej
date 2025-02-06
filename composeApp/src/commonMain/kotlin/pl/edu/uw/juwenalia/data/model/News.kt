package pl.edu.uw.juwenalia.data.model

data class News(
    var id: Int,
    val title: String,
    val imageFilename: String,
    val content: String,
    val isDarkText: Boolean,
    val imageByteArray: ByteArray
)