package pl.edu.uw.juwenalia.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getAppFilesDirectory(): String {
    val context: Context = LocalContext.current
    return context.filesDir.absolutePath
}