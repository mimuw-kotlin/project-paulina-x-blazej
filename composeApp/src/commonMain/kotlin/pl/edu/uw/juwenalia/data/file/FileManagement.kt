package pl.edu.uw.juwenalia.data.file

import io.github.vinceglb.filekit.core.PlatformFile
import okio.FileSystem
import okio.SYSTEM
import okio.buffer
import okio.use

suspend fun savePickedFile(
    filesDir: String,
    folder: String,
    file: PlatformFile
) {
    val filePath = getPath(filesDir, folder, file.name)
    checkPathExistence(filesDir, folder)

    val fileBytes = file.readBytes()
    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun saveJsonFile(
    filesDir: String,
    folder: String,
    fileName: String,
    contents: String
) {
    val filePath = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)
    FileSystem.SYSTEM
        .sink(filePath)
        .buffer()
        .use { sink -> sink.writeUtf8(contents) }
}

fun saveFile(
    filesDir: String,
    folder: String,
    fileName: String,
    fileBytes: ByteArray
) {
    val filePath = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)

    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun deleteFile(
    filesDir: String,
    folder: String,
    fileName: String
) {
    val fileSystem = FileSystem.SYSTEM
    val fileToDelete = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)

    fileSystem.delete(fileToDelete)
}

fun getFileBytesByName(
    filesDir: String,
    folder: String,
    fileName: String
): ByteArray? {
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

fun getJsonString(
    filesDir: String,
    folder: String,
    fileName: String
): String? {
    val filePath = getPath(filesDir, folder, fileName)
    checkPathExistence(filesDir, folder)

    return try {
        FileSystem.SYSTEM
            .source(filePath)
            .buffer()
            .readUtf8()
    } catch (e: Exception) {
        println("Error reading json: ${e.message}")
        null
    }
}

fun getFiles(
    filesDir: String,
    folder: String
): List<String> {
    val fileSystem = FileSystem.SYSTEM
    val directory = getPath(filesDir, folder)
    checkPathExistence(filesDir, folder)

    val files = fileSystem.list(directory)

    return files.map { it.name }
}