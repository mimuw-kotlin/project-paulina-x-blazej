package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.runtime.Composable
import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual fun openFile(filePath : String, context : Any?) {
    val url = NSURL(filePath)
    val app = UIApplication.sharedApplication()
    app.openURL(url)
}

@Composable
actual fun getContext(): Any? {
    return null
}