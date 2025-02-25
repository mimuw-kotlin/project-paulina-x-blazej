package pl.edu.uw.juwenalia.data.file

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM

expect fun getAppFilesDirectory(): String

fun getPath(
    folder: String,
    filename: String? = null
): Path =
    when (filename) {
        null -> "${getAppFilesDirectory()}/$folder".toPath()
        else -> "${getAppFilesDirectory()}/$folder/$filename".toPath()
    }

fun checkPathExistence(folder: String) {
    val expectedPath = getPath(folder)
    val fileSystem = FileSystem.SYSTEM
    if (!fileSystem.exists(expectedPath)) {
        fileSystem.createDirectory(expectedPath)
    }
}

fun checkFileExistence(
    folder: String,
    fileName: String
): Boolean {
    val expectedPath = getPath(folder, fileName)
    val fileSystem = FileSystem.SYSTEM
    return fileSystem.exists(expectedPath)
}