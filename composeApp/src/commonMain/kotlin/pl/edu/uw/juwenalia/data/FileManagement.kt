package pl.edu.uw.juwenalia.data

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformFile
import io.ktor.client.statement.HttpResponse
import okio.FileSystem
import okio.Path
import okio.SYSTEM
import okio.buffer
import okio.use

suspend fun savePickedFile(filesDir: String, file: PlatformFile) {
    val filePath = getPath(filesDir, FolderEnum.TICKET_RESOURCES, file.name)
    checkPathExistence(filesDir, FolderEnum.TICKET_RESOURCES)

    val fileBytes = file.readBytes()
    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun saveFile(filesDir: String, folder: FolderEnum,
                     fileName: String, fileBytes: ByteArray) {
    val filePath = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)

    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes) }
}

fun deleteFile(filesDir: String, folder: FolderEnum, fileName: String) {
    val fileSystem = FileSystem.SYSTEM
    val fileToDelete = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)

    fileSystem.delete(fileToDelete)
}

fun getFileBytesByName(filesDir: String, folder: FolderEnum, fileName: String): ByteArray? {
    val filePath = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)

    return try {
        FileSystem.SYSTEM.source(filePath).buffer().use { source ->
            source.readByteArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getFileSet(filesDir: String, folder: FolderEnum) : Set<String> {
    val fileSystem = FileSystem.SYSTEM
    val directory = getPath(filesDir, folder)
    checkPathExistence(filesDir, folder)

    val files = fileSystem.list(directory)

    return files.map { it.name }.toSet()
}