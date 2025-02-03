package pl.edu.uw.juwenalia.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import pl.edu.uw.juwenalia.data.file.checkFileExistence
import pl.edu.uw.juwenalia.data.file.deleteFile
import pl.edu.uw.juwenalia.data.file.getFiles
import pl.edu.uw.juwenalia.data.file.getJsonString
import pl.edu.uw.juwenalia.data.file.saveFile
import pl.edu.uw.juwenalia.data.file.saveJsonFile

private const val FEED_SOURCE: String = "https://c00kiepreferences.github.io/JuweFeed"
private const val HOME_FOLDER: String = "Feed"
private const val JSON_FOLDER: String = "Json"
private const val IMAGES_FOLDER: String = "Pictures"
private const val FEED_FILENAME: String = "sample_feed.json"
private const val FEED_VERSION_FILENAME: String = "feed_version.json"

@Serializable
data class NewsData(
    val title: String = "",
    val image: String = ""
)

@Serializable
data class ArtistData(
    val name: String = "",
    val image: String = ""
)

@Serializable
data class FeedData(
    val id: Int = 0,
    val newsData: List<NewsData> = emptyList(),
    val artistData: List<ArtistData> = emptyList()
)

@Serializable
data class FeedVersionData(
    val id: Int = 0
)

// Save the current feed version in a feed version file
fun saveCurrentFeedVersion(filesDir: String) {
    if (checkFileExistence(filesDir, "json", FEED_FILENAME)) {
        val currentFeedString = getJsonString(filesDir, "json", FEED_FILENAME)
        if (currentFeedString != null) {
            val currFeedData: FeedData = Json.decodeFromString(currentFeedString)
            val currFeedVersionData = FeedVersionData(id = currFeedData.id)
            val currFeedVersionString = Json.encodeToString(currFeedVersionData)
            saveJsonFile(filesDir, "json", FEED_VERSION_FILENAME, currFeedVersionString)
        }
    }
}

// If previous version is recorded and has a different id, return false
fun compareFeedVersions(
    filesDir: String,
    currId: Int
): Boolean {
    if (!checkFileExistence(filesDir, "json", FEED_VERSION_FILENAME)) return true
    val feedVersionString = getJsonString(filesDir, "json", FEED_VERSION_FILENAME)

    if (feedVersionString == null) return true
    val feedVersionData: FeedVersionData = Json.decodeFromString(feedVersionString)

    if (feedVersionData.id == currId) return true
    return false
}

// Download feed info and return download status
suspend fun downloadFeed(filesDir: String): Boolean {
    // before download, save current version id (if exists)
    saveCurrentFeedVersion(filesDir)

    val url = "$FEED_SOURCE/$HOME_FOLDER/$JSON_FOLDER/$FEED_FILENAME"
    if (!downloadFile(filesDir, "json", FEED_FILENAME, url)) return false

    val feedData: FeedData
    val feedString = getJsonString(filesDir, "json", FEED_FILENAME)

    if (feedString == null) {
        return false
    } else {
        feedData = Json.decodeFromString(feedString)
    }

    // if downloaded json id is the same as previous versions, return
    if (compareFeedVersions(filesDir, feedData.id)) {
        return true
    }

    // if versions differ, sync feed pictures
    downloadAllPictures(filesDir, feedData)
    deleteRedundantPictures(filesDir, feedData)

    return true
}

// Download json file describing feed
suspend fun downloadFile(
    filesDir: String,
    folder: String,
    fileName: String,
    url: String
): Boolean {
    var success = true
    withContext(Dispatchers.IO) {
        val client =
            HttpClient(CIO) {
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
suspend fun downloadAllPictures(
    filesDir: String,
    feedData: FeedData
) {
    feedData.newsData.forEach { news ->
        val url = "$FEED_SOURCE/$HOME_FOLDER/$IMAGES_FOLDER/${news.image}"
        downloadFile(filesDir, "artist_images", news.image, url)
    }
    feedData.artistData.forEach { artist ->
        val url = "$FEED_SOURCE/$HOME_FOLDER/$IMAGES_FOLDER/${artist.image}"
        downloadFile(filesDir, "artist_images", artist.image, url)
    }
}

// Delete redundant pictures
fun deleteRedundantPictures(
    filesDir: String,
    feedData: FeedData
) {
    val neededImages: MutableSet<String> = emptySet<String>().toMutableSet()
    feedData.newsData.forEach { news -> neededImages += news.image }
    feedData.artistData.forEach { artist -> neededImages += artist.image }
    val allImages = getFiles(filesDir, "artist_images")

    allImages.forEach { image ->
        if (!neededImages.contains(image)) {
            deleteFile(filesDir, "artist_images", image)
        }
    }
}

fun getFeedData(filesDir: String): FeedData {
    if (!checkFileExistence(filesDir, "json", FEED_FILENAME)) {
        return FeedData()
    }
    val feedString = getJsonString(filesDir, "json", FEED_FILENAME)
    return if (feedString != null) {
        Json.decodeFromString(feedString)
    } else {
        FeedData()
    }
}

fun getNews(filesDir: String): List<NewsData> = getFeedData(filesDir).newsData

fun getArtists(filesDir: String): List<ArtistData> = getFeedData(filesDir).artistData