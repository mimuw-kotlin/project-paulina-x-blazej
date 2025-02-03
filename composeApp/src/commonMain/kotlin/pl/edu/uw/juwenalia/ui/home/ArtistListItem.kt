package pl.edu.uw.juwenalia.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.edu.uw.juwenalia.data.file.getFileBytesByName

@Composable
internal fun ArtistListItem(
    name: String,
    filesDir: String,
    fileName: String,
    onClick: () -> Unit
) {
    val fileBytes = getFileBytesByName(filesDir, "artist_images", fileName)

    Column(
        modifier = Modifier.width(128.dp).wrapContentHeight().clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (fileBytes != null) {
            AsyncImage(
                fileBytes,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .clip(shape = CircleShape)
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall
        )
    }
}