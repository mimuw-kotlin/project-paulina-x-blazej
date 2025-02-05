package pl.edu.uw.juwenalia.ui.home.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.edu.uw.juwenalia.data.model.News

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun NewsGridItem(
    news: News,
    imageContentDescription: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                news.imageByteArray,
                contentDescription = imageContentDescription,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(
                                key = "news-image-${news.id}"
                            ),
                            animatedVisibilityScope = animatedContentScope
                        ).fillMaxWidth()
                        .aspectRatio(1.77f) // 16:9 aspect ratio
                        .clip(shape = RoundedCornerShape(12.dp))
            )

            Surface(
                modifier =
                    Modifier
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(
                                key = "news-title-${news.id}"
                            ),
                            animatedVisibilityScope = animatedContentScope
                        ).padding(16.dp)
                        .align(Alignment.BottomStart)
                        .clip(
                            shape = RoundedCornerShape(12.dp)
                        )
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier =
                        Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}