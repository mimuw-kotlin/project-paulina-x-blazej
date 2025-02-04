package pl.edu.uw.juwenalia.data.file

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM

expect fun getAppFilesDirectory(): String

sealed class Async<out T> {
    object Loading : Async<Nothing>()

    data class Error(
        val errorMessage: Int
    ) : Async<Nothing>()

    data class Success<out T>(
        val data: T
    ) : Async<T>()
}

fun getPath(
    filesDir: String,
    folder: String,
    filename: String? = null
): Path =
    when (filename) {
        null -> "$filesDir/$folder".toPath()
        else -> "$filesDir/$folder/$filename".toPath()
    }

fun checkPathExistence(
    filesDir: String,
    folder: String
) {
    val expectedPath = getPath(filesDir, folder)
    val fileSystem = FileSystem.SYSTEM
    if (!fileSystem.exists(expectedPath)) {
        fileSystem.createDirectory(expectedPath)
    }
}

fun checkFileExistence(
    filesDir: String,
    folder: String,
    fileName: String
): Boolean {
    val expectedPath = getPath(filesDir, folder, fileName)
    val fileSystem = FileSystem.SYSTEM
    return fileSystem.exists(expectedPath)
}