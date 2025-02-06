package pl.edu.uw.juwenalia.ui.artists.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.edu.uw.juwenalia.data.model.Artist

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ArtistItem(
    artist: Artist,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Column(
        modifier =
            Modifier
                .width(128.dp)
                .wrapContentHeight()
                .clickable(onClick = onClick)
                .testTag("artist_item_${artist.id}")
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                artist.imageByteArray,
                contentDescription = artist.name,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(
                                key = "artist-image-${artist.id}"
                            ),
                            animatedVisibilityScope = animatedContentScope
                        ).padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .testTag("artistItem-${artist.id}")
            )
            Text(
                text = artist.name,
                style = MaterialTheme.typography.titleMedium,
                modifier =
                    Modifier
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(
                                key = "artist-name-${artist.id}"
                            ),
                            animatedVisibilityScope = animatedContentScope
                        )
            )
        }
        Text(
            text = artist.time,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}