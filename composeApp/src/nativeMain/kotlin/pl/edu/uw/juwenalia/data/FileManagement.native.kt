package pl.edu.uw.juwenalia.data

import androidx.compose.runtime.Composable
import platform.Foundation.*

@Composable
actual fun getAppFilesDirectory(): String {
    return NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory, NSUserDomainMask, true
    ).first() as String
}