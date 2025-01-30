package pl.edu.uw.juwenalia.data

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformFile
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use

private const val TICKET_RESOURCES_FOLDER = "ticket_resources"

@Composable
expect fun getAppFilesDirectory(): String

fun checkTicketPath(filesDir: String) {
    val expectedPath = "$filesDir/$TICKET_RESOURCES_FOLDER".toPath()
    val fileSystem = FileSystem.SYSTEM
    if (!fileSystem.exists(expectedPath)) {
        fileSystem.createDirectory(expectedPath)
    }
}

suspend fun saveFile(file: PlatformFile, filesDir: String) {
    val filePath = "$filesDir/$TICKET_RESOURCES_FOLDER/${file.name}".toPath()
    checkTicketPath(filesDir)

    val fileBytes = file.readBytes()
    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun deleteFile(fileName: String, filesDir: String) {
    val fileSystem = FileSystem.SYSTEM
    val directory = filesDir.toPath()
    val fileToDelete = directory / TICKET_RESOURCES_FOLDER / fileName
    checkTicketPath(filesDir)

    fileSystem.delete(fileToDelete)
}

fun getFileBytesByName(fileName: String, filesDir: String): ByteArray? {
    val filePath = "$filesDir/$TICKET_RESOURCES_FOLDER/${fileName}".toPath()
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
    val directory = "$filesDir/$TICKET_RESOURCES_FOLDER".toPath()
    checkTicketPath(filesDir)

    val files = fileSystem.list(directory)

    return files.map { it.name }.toSet()
}