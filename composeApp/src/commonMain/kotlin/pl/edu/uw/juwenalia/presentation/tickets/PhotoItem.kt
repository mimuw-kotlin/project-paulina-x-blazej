package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.edu.uw.juwenalia.data.getFileBytesByName

@Composable
internal fun PhotoItem(fileName: String, filesDir: String) {
    val fileBytes = getFileBytesByName(fileName, filesDir)

    fileBytes?.let {
        AsyncImage(
            fileBytes,
            contentDescription = fileName,
            contentScale = ContentScale.Fit,
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        )
    }
}