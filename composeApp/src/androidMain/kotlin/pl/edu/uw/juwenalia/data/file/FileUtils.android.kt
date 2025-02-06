package pl.edu.uw.juwenalia.data.file

import android.content.Context
import org.koin.java.KoinJavaComponent.getKoin

actual fun getAppFilesDirectory(): String {
    val context: Context = getKoin().get()
    return context.filesDir.absolutePath
}