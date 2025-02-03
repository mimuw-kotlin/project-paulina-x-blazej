package pl.edu.uw.juwenalia.ui.tickets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.edu.uw.juwenalia.data.FolderEnum
import pl.edu.uw.juwenalia.data.getFileBytesByName

@Composable
internal fun PhotoItem(
    filesDir: String,
    fileName: String
) {
    val fileBytes = getFileBytesByName(filesDir, FolderEnum.TICKET_RESOURCES, fileName)

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