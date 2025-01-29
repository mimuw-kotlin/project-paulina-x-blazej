package pl.edu.uw.juwenalia.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
internal fun SponsorListItem(
    name: String,
    image: DrawableResource,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(96.dp).wrapContentHeight().clickable(onClick = onClick)
    ) {
        Box(
            modifier =
                Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Image(
                bitmap = imageResource(image),
                contentDescription = name,
                modifier =
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall
        )
    }
}