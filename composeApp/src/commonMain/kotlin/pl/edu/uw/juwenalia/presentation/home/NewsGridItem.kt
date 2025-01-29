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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
internal fun NewsGridItem(
    title: String,
    darkTextColor: Boolean,
    image: DrawableResource,
    imageContentDescription: String,
    onClick: () -> Unit
) {
    Box(
        Modifier.clickable(onClick = onClick)
    ) {
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