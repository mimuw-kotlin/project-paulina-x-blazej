package pl.edu.uw.juwenalia.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.serialization.Serializable

private const val feedSource: String = "https://c00kiepreferences.github.io/JuweFeed"
private const val homeFolder: String = "Feed"
private const val jsonFolder: String = "Json"
private const val imagesFolder: String = "Pictures"
private const val feedFileName: String = "sample_feed.json"

@Serializable
data class NewsData (
    val title: String = "",
    val image: String = ""
)

@Serializable
data class ArtistData (
    val name: String = "",
    val image: String = ""
)

@Serializable
data class FeedData (
    val newsData: List<NewsData>,
    val artistData: List<ArtistData>
)

suspend fun downloadFeedJson(filesDir: String) {
    val client = HttpClient() {
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    val url = "$feedSource/$homeFolder/$jsonFolder/$feedFileName"
    val fileBytes: ByteArray = client.get(url).body()

//    saveFile(filesDir, FolderEnum.JSON, feedFileName, fileBytes)
}