package pl.edu.uw.juwenalia.data

import androidx.compose.runtime.Composable
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM

@Composable
expect fun getAppFilesDirectory(): String

enum class FolderEnum(val value: String) {
    TICKET_RESOURCES("ticket_resources"),
    JSON("json"),
    ARTIST_IMAGES("artist_images")
}

fun getPath(filesDir: String, folder: FolderEnum, filename: String? = null) : Path {
    return when(filename) {
        null-> "$filesDir/${folder.value}".toPath()
        else-> "$filesDir/${folder.value}/$filename".toPath()
    }
}

fun checkPathExistence(filesDir: String, folder: FolderEnum) {
    val expectedPath = getPath(filesDir, folder)
    val fileSystem = FileSystem.SYSTEM
    if (!fileSystem.exists(expectedPath)) {
        fileSystem.createDirectory(expectedPath)
    }
}