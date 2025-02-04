package pl.edu.uw.juwenalia.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsJsonData(
    val title: String,
    val imageFilename: String
)