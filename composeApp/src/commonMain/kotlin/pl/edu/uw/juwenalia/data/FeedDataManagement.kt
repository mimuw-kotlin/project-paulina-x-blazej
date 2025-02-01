package pl.edu.uw.juwenalia.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val feedSource: String = "https://c00kiepreferences.github.io/JuweFeed"
private const val homeFolder: String = "Feed"
private const val jsonFolder: String = "Json"
private const val imagesFolder: String = "Pictures"
private const val feedFileName: String = "sample_feed.json"
private const val feedVersionFileName: String = "feed_version.json"

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
    val id: Int = 0,
    val newsData: List<NewsData> = emptyList(),
    val artistData: List<ArtistData> = emptyList()
)

@Serializable
data class FeedVersionData (
    val id: Int = 0
)

// Save current feed version in feed version file
fun saveCurrentFeedVersion(filesDir: String) {
    if (checkFileExistence(filesDir, FolderEnum.JSON, feedFileName)) {
        val currentFeedString = getJsonString(filesDir, FolderEnum.JSON, feedFileName)
        if (currentFeedString != null) {
            val currFeedData: FeedData = Json.decodeFromString(currentFeedString)
            val currFeedVersionData = FeedVersionData(id = currFeedData.id)
            val currFeedVersionString = Json.encodeToString(currFeedVersionData)
            saveJsonFile(filesDir, FolderEnum.JSON, feedVersionFileName, currFeedVersionString)
        }
    }
}

// If previous version is recorded and has a different id, return false
fun compareFeedVersions(filesDir: String, currId: Int): Boolean {
    if (!checkFileExistence(filesDir, FolderEnum.JSON, feedVersionFileName)) return true
    val feedVersionString = getJsonString(filesDir, FolderEnum.JSON, feedVersionFileName)

    if (feedVersionString == null) return true
    val feedVersionData: FeedVersionData = Json.decodeFromString(feedVersionString)

    if (feedVersionData.id == currId) return true
    return false
}

// Download feed info and return download status
suspend fun downloadFeed(filesDir: String): Boolean {

    // before download, save current version id (if exists)
    saveCurrentFeedVersion(filesDir)

    val url = "$feedSource/$homeFolder/$jsonFolder/$feedFileName"
    if (!downloadFile(filesDir, FolderEnum.JSON, feedFileName, url)) return false

    val feedData: FeedData
    val feedString = getJsonString(filesDir, FolderEnum.JSON, feedFileName)

    if (feedString == null) return false
    else feedData = Json.decodeFromString(feedString)

    // if downloaded json id is the same as previous versions, return
    if(compareFeedVersions(filesDir, feedData.id)) {
        return true
    }

    // if versions differ, sync feed pictures
    downloadAllPictures(filesDir, feedData)
    deleteRedundantPictures(filesDir, feedData)

    return true
}

// Download json file describing feed
suspend fun downloadFile(filesDir: String, folder: FolderEnum,
                         fileName: String, url: String): Boolean {
    var success = true
    withContext(Dispatchers.IO) {
        val client = HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }

        try {
            val fileBytes: ByteArray = client.get(url).body()
            saveFile(filesDir, folder, fileName, fileBytes)
        } catch (e: Exception) {
            println("Feed download error: ${e.message}")
            success = false
        } finally {
            client.close()
        }

    }
    return success
}

// Download all pictures necessary to generate feed
suspend fun downloadAllPictures(filesDir: String, feedData: FeedData) {
    feedData.newsData.forEach { news ->
        val url = "$feedSource/$homeFolder/$imagesFolder/${news.image}"
        downloadFile(filesDir, FolderEnum.IMAGES, news.image, url)
    }
    feedData.artistData.forEach { artist ->
        val url = "$feedSource/$homeFolder/$imagesFolder/${artist.image}"
        downloadFile(filesDir, FolderEnum.IMAGES, artist.image, url)
    }
}

// Delete redundant pictures
fun deleteRedundantPictures(filesDir: String, feedData: FeedData) {
    val neededImages: MutableSet<String> = emptySet<String>().toMutableSet()
    feedData.newsData.forEach { news -> neededImages += news.image }
    feedData.artistData.forEach { artist -> neededImages += artist.image }
    val allImages = getFileSet(filesDir, FolderEnum.IMAGES)

    allImages.forEach { image ->
        if (!neededImages.contains(image)) {
            deleteFile(filesDir, FolderEnum.IMAGES, image)
        }
    }
}

fun getFeedData(filesDir: String): FeedData {
    if (!checkFileExistence(filesDir, FolderEnum.JSON, feedFileName)) {
        return FeedData()
    }
    val feedString = getJsonString(filesDir, FolderEnum.JSON, feedFileName)
    return if (feedString != null) {
        Json.decodeFromString(feedString)
    }
    else FeedData()
}

fun getNews(filesDir: String): List<NewsData> {
    return getFeedData(filesDir).newsData
}

fun getArtists(filesDir: String): List<ArtistData> {
    return getFeedData(filesDir).artistData
}