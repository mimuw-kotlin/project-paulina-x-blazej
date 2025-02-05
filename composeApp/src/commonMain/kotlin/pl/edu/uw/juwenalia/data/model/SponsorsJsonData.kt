package pl.edu.uw.juwenalia.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SponsorsJsonData(
    val id: Int = 0,
    val name: String = "",
    val imageFilename: String = "",
    val url: String = ""
)

