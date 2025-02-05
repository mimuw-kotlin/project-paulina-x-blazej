package pl.edu.uw.juwenalia.ui.home.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import pl.edu.uw.juwenalia.data.model.Artist

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ArtistListItem(
    artist: Artist,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
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
                        .clip(shape = CircleShape)
            )
            Text(
                text = artist.name,
                style = MaterialTheme.typography.titleSmall,
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
    }
}