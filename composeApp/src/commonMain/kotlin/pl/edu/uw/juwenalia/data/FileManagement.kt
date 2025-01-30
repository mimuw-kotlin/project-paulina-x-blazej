package pl.edu.uw.juwenalia.data

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformFile
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use

@Composable
expect fun getAppFilesDirectory(): String

suspend fun saveFile(file: PlatformFile, filesDir: String) {
    val filePath = "$filesDir/${file.name}".toPath()

    val fileBytes = file.readBytes()
    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun deleteFile(fileName: String, filesDir: String) {
    val fileSystem = FileSystem.SYSTEM
    val directory = filesDir.toPath()
    val fileToDelete = directory / fileName

    fileSystem.delete(fileToDelete)
}

fun getFileBytesByName(fileName: String, filesDir: String): ByteArray? {
    val filePath = "$filesDir/${fileName}".toPath()

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
    val directory = filesDir.toPath()

    val files = fileSystem.list(directory)

    return files.map { it.name }.toSet()
}