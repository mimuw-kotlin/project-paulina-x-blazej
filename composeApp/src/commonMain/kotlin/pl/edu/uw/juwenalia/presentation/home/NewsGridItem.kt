package pl.edu.uw.juwenalia.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import pl.edu.uw.juwenalia.data.FolderEnum
import pl.edu.uw.juwenalia.data.getFileBytesByName

@Composable
internal fun NewsGridItem(
    title: String,
    darkTextColor: Boolean,
    image: DrawableResource? = null,
    filesDir: String? = null,
    fileName: String? = null,
    imageContentDescription: String,
    onClick: () -> Unit
) {
    var fileBytes: ByteArray? = null
    if (filesDir != null && fileName != null) {
        fileBytes = getFileBytesByName(filesDir, FolderEnum.IMAGES, fileName)
    }

    Box(
        Modifier.clickable(onClick = onClick)
    ) {
        if (fileBytes != null) {
            AsyncImage(
                fileBytes,
                contentDescription = imageContentDescription,
                contentScale = ContentScale.Crop,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.77f) // 16:9 aspect ratio
                    .clip(shape = RoundedCornerShape(12.dp)),
            )
        }
        else if (image != null) {
            Image(
                bitmap = imageResource(image),
                contentDescription = imageContentDescription,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.77f) // 16:9 aspect ratio
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color =
            if (darkTextColor) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.inverseOnSurface
            },
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        )
    }
}