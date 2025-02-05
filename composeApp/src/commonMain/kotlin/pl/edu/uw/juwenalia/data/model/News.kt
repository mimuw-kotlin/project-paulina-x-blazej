package pl.edu.uw.juwenalia.data.model

data class News(
    val id: Int,
    val title: String,
    val imageFilename: String,
    val imageByteArray: ByteArray
)