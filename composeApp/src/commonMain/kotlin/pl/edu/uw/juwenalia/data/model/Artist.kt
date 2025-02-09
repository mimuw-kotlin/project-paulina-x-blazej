package pl.edu.uw.juwenalia.data.model

data class Artist(
    var id: Int,
    val name: String,
    val imageFilename: String,
    val description: String,
    val day: Int,
    val time: String,
    val imageByteArray: ByteArray
)