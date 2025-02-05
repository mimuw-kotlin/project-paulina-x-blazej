package pl.edu.uw.juwenalia.data.model

data class Sponsor(
    val id: Int,
    val name: String,
    val imageFilename: String,
    val url: String,
    val imageByteArray: ByteArray
)