package pl.edu.uw.juwenalia.data

import kotlinx.serialization.Serializable

private const val feedSource: String = "https://c00kiepreferences.github.io/JuweFeed"
private const val jsonFolder: String = "Feed"
private const val imagesFolder: String = "Pictures"

@Serializable
data class NewsData (
    val title: String,
    val image: String
)

@Serializable
data class ArtistData (
    val name: String,
    val image: String
)

@Serializable
data class Feed (
    val newsData: List<NewsData>,
    val artistData: List<ArtistData>
)

