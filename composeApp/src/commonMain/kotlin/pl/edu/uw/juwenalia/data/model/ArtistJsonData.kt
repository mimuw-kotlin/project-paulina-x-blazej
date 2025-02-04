package pl.edu.uw.juwenalia.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistJsonData(
    val name: String,
    val imageFilename: String
)

