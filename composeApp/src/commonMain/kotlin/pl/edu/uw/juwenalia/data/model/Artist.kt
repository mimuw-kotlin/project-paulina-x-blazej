package pl.edu.uw.juwenalia.data.model

data class Artist(
    val id: Int,
    val name: String,
    val imageFilename: String,
    val description: String,
    val imageByteArray: ByteArray
)