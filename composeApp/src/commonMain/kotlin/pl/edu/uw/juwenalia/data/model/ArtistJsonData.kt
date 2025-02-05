package pl.edu.uw.juwenalia.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistJsonData(
    val id: Int = 0,
    val name: String = "",
    val imageFilename: String = ""
)

