package pl.edu.uw.juwenalia.data

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformFile
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use

enum class FolderEnum(val value: String) {
    TICKET_RESOURCES("ticket_resources"),
    JSON("json"),
    ARTIST_IMAGES("artist_images")
}

@Composable
expect fun getAppFilesDirectory(): String

fun getPath(filesDir: String, folder: String, filename: String? = null) : Path {
    return when(filename) {
        null-> "$filesDir/$folder".toPath()
        else-> "$filesDir/$folder/$filename".toPath()
    }
}

fun checkTicketPath(filesDir: String) {
    val expectedPath = getPath(filesDir, FolderEnum.TICKET_RESOURCES.value)
    val fileSystem = FileSystem.SYSTEM
    if (!fileSystem.exists(expectedPath)) {
        fileSystem.createDirectory(expectedPath)
    }
}

suspend fun saveFile(file: PlatformFile, filesDir: String) {
    val filePath = getPath(filesDir, FolderEnum.TICKET_RESOURCES.value, file.name)
    checkTicketPath(filesDir)

    val fileBytes = file.readBytes()
    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun deleteFile(fileName: String, filesDir: String) {
    val fileSystem = FileSystem.SYSTEM
    val fileToDelete = getPath(filesDir, FolderEnum.TICKET_RESOURCES.value, fileName)
    checkTicketPath(filesDir)

    fileSystem.delete(fileToDelete)
}

fun getFileBytesByName(fileName: String, filesDir: String): ByteArray? {
    val filePath = "$filesDir/${FolderEnum.TICKET_RESOURCES}/${fileName}".toPath()
    checkTicketPath(filesDir)

    return try {
        FileSystem.SYSTEM.source(filePath).buffer().use { source ->
            source.readByteArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getFileSet(filesDir: String) : Set<String> {
    val fileSystem = FileSystem.SYSTEM
    val directory = getPath(filesDir, FolderEnum.TICKET_RESOURCES.value)
    checkTicketPath(filesDir)

    val files = fileSystem.list(directory)

    return files.map { it.name }.toSet()
}