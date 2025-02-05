package pl.edu.uw.juwenalia.data.file

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import pl.edu.uw.juwenalia.data.model.ArtistJsonData
import pl.edu.uw.juwenalia.data.model.NewsJsonData
import pl.edu.uw.juwenalia.data.model.SponsorsJsonData

private const val FEED_SOURCE_URL: String = "https://c00kiepreferences.github.io/JuweFeed"
private const val REMOTE_HOME_FOLDER: String = "Feed"
private const val JSON_FOLDER: String = "Json"
private const val REMOTE_IMAGES_FOLDER: String = "Pictures"
private const val FEED_FILENAME: String = "feed.json"
private const val FEED_VERSION_FILENAME: String = "feed_version.json"
const val ARTIST_IMAGES_FOLDER: String = "artist_images"
const val NEWS_IMAGES_FOLDER: String = "news_images"
const val SPONSOR_IMAGES_FOLDER: String = "sponsor_images"
private val json = Json { ignoreUnknownKeys = true }

@Serializable
data class FeedData(
    val id: Int = 0,
    val newsData: List<NewsJsonData> = emptyList(),
    val artistData: List<ArtistJsonData> = emptyList(),
    val sponsorsData: List<SponsorsJsonData> = emptyList()
)

@Serializable
data class FeedVersionData(
    val id: Int = 0
)

// Save the current feed version in a feed version file
fun saveCurrentFeedVersion() {
    if (checkFileExistence(JSON_FOLDER, FEED_FILENAME)) {
        val currentFeedString = getJsonString(JSON_FOLDER, FEED_FILENAME)
        if (currentFeedString != null) {
            val currFeedData: FeedData = json.decodeFromString(currentFeedString)
            val currFeedVersionData = FeedVersionData(id = currFeedData.id)
            val currFeedVersionString = Json.encodeToString(currFeedVersionData)
            saveJsonFile(JSON_FOLDER, FEED_VERSION_FILENAME, currFeedVersionString)
        }
    }
}

// If previous version of feed has a different id or is not recorded, return false
fun compareFeedVersions(
    currId: Int
): Boolean {
    if (!checkFileExistence(JSON_FOLDER, FEED_VERSION_FILENAME)) return false
    val feedVersionString = getJsonString(JSON_FOLDER, FEED_VERSION_FILENAME)

    if (feedVersionString == null) return true
    val feedVersionData: FeedVersionData = json.decodeFromString(feedVersionString)

    if (feedVersionData.id == currId) return true
    return false
}

// Download feed info and return download status
suspend fun downloadFeed(): Boolean {
    // before download, save current version id (if exists)
    saveCurrentFeedVersion()

    val url = "$FEED_SOURCE_URL/$REMOTE_HOME_FOLDER/$JSON_FOLDER/$FEED_FILENAME"
    if (!downloadFile(JSON_FOLDER, FEED_FILENAME, url)) return false

    val feedData: FeedData
    val feedString = getJsonString(JSON_FOLDER, FEED_FILENAME)

    if (feedString == null) {
        return false
    } else {
        feedData = json.decodeFromString(feedString)
    }

    // if versions differ, sync feed pictures
    downloadAllPictures(feedData)
    deleteRedundantPictures(feedData)

    return true
}

// Download json file describing feed
suspend fun downloadFile(
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
            if (client.get(url).status != HttpStatusCode.OK) {
                success = false
            } else {
                val fileBytes: ByteArray = client.get(url).body()
                saveFile(folder, fileName, fileBytes)
            }
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
    feedData: FeedData
) {
    feedData.newsData.forEach { news ->
        val url = "$FEED_SOURCE_URL/$REMOTE_HOME_FOLDER/$REMOTE_IMAGES_FOLDER/${news.imageFilename}"
        downloadFile(NEWS_IMAGES_FOLDER, news.imageFilename, url)
    }
    feedData.artistData.forEach { artist ->
        val url = "$FEED_SOURCE_URL/$REMOTE_HOME_FOLDER/$REMOTE_IMAGES_FOLDER/${artist.imageFilename}"
        downloadFile(ARTIST_IMAGES_FOLDER, artist.imageFilename, url)
    }
    feedData.sponsorsData.forEach { sponsor ->
        val url = "$FEED_SOURCE_URL/$REMOTE_HOME_FOLDER/$REMOTE_IMAGES_FOLDER/${sponsor.imageFilename}"
        downloadFile(SPONSOR_IMAGES_FOLDER, sponsor.imageFilename, url)
    }
}

// Delete redundant pictures
fun deleteRedundantPictures(
    feedData: FeedData
) {
    val neededImages: MutableSet<String> = emptySet<String>().toMutableSet()
    feedData.newsData.forEach { news -> neededImages += news.imageFilename }
    feedData.artistData.forEach { artist -> neededImages += artist.imageFilename }
    feedData.sponsorsData.forEach { sponsor -> neededImages += sponsor.imageFilename }
    val allArtistImages = getFiles(ARTIST_IMAGES_FOLDER)
    val allNewsImages = getFiles(NEWS_IMAGES_FOLDER)
    val allSponsorImages = getFiles(SPONSOR_IMAGES_FOLDER)

    allArtistImages.forEach { image ->
        if (!neededImages.contains(image)) {
            deleteFile(ARTIST_IMAGES_FOLDER, image)
        }
    }
    allNewsImages.forEach { image ->
        if (!neededImages.contains(image)) {
            deleteFile(NEWS_IMAGES_FOLDER, image)
        }
    }
    allNewsImages.forEach { sponsor ->
        if (!neededImages.contains(sponsor)) {
            deleteFile(SPONSOR_IMAGES_FOLDER, sponsor)
        }
    }
}

fun getFeedData(): FeedData {
    if (!checkFileExistence(JSON_FOLDER, FEED_FILENAME)) {
        return FeedData()
    }
    val feedString = getJsonString(JSON_FOLDER, FEED_FILENAME)
    return if (feedString != null) {
        json.decodeFromString(feedString)
    } else {
        FeedData()
    }
}