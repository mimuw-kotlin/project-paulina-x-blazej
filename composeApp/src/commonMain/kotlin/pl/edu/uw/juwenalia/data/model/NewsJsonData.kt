package pl.edu.uw.juwenalia.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsJsonData(
    val id: Int,
    val title: String,
    val imageFilename: String
)