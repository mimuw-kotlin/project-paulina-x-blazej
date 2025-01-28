package pl.edu.uw.juwenalia.presentation.tickets

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual fun openFile(filePath: String, context : Any?) {
    Log.d("FileOpener", "openFile started")

    if (context is Context) {
        Log.d("FileOpener", "we have context")

        // Tworzymy URI dla pliku
        val fileUri = Uri.parse(filePath)
        Log.d("FileOpener", "filePath: $filePath")

        // Tworzymy intencję do otwarcia pliku PDF
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = fileUri
            setDataAndType(fileUri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // Sprawdzamy, czy istnieje aplikacja, która obsłuży ten plik
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent) // Uruchamiamy aplikację
        } else {
            Log.d("FileOpener", "No app to handle PDF")
        }
    }

    Log.d("FileOpener", "openFile finished")
}

@Composable
actual fun getContext(): Any? {
    return LocalContext.current
}