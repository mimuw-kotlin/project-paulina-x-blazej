package pl.edu.uw.juwenalia.data.file

import io.github.vinceglb.filekit.core.PlatformFile
import okio.FileSystem
import okio.SYSTEM
import okio.buffer
import okio.use

suspend fun savePickedFile(
    folder: String,
    file: PlatformFile
) {
    val filePath = getPath(folder, file.name)
    checkPathExistence(folder)

    val fileBytes = file.readBytes()
    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun saveJsonFile(
    folder: String,
    fileName: String,
    contents: String
) {
    val filePath = getPath(folder, fileName)
    checkPathExistence(folder)
    FileSystem.SYSTEM
        .sink(filePath)
        .buffer()
        .use { sink -> sink.writeUtf8(contents) }
}

fun saveFile(
    folder: String,
    fileName: String,
    fileBytes: ByteArray
) {
    val filePath = getPath(folder, fileName)
    checkPathExistence(folder)

    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.write(fileBytes)
    }
}

fun deleteFile(
    folder: String,
    fileName: String
) {
    val fileSystem = FileSystem.SYSTEM
    val fileToDelete = getPath(folder, fileName)
    checkPathExistence(folder)

    fileSystem.delete(fileToDelete)
}

fun getFileBytesByName(
    folder: String,
    fileName: String
): ByteArray? {
    val filePath = getPath(folder, fileName)
    if (!checkFileExistence(folder, fileName)) return null

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
    folder: String,
    fileName: String
): String? {
    val filePath = getPath(folder, fileName)
    checkPathExistence(folder)

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
    folder: String
): List<String> {
    val fileSystem = FileSystem.SYSTEM
    val directory = getPath(folder)
    checkPathExistence(folder)

    val files = fileSystem.list(directory)

    return files.map { it.name }
}