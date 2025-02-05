package pl.edu.uw.juwenalia.ui.artists

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.artist_photo_placeholder
import juweappka.composeapp.generated.resources.artists_title
import juweappka.composeapp.generated.resources.back
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.data.model.Artist

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun ArtistDetailScreen(
    artist: Artist,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.artists_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding)
        ) {
            Image(
                imageResource(Res.drawable.artist_photo_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .blur(20.dp)
                        .fillMaxWidth()
                        .height(152.dp)
                        .align(Alignment.TopCenter)
            )
            Column(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 52.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                with(sharedTransitionScope) {
                    Image(
                        imageResource(Res.drawable.artist_photo_placeholder),
                        contentDescription = artist.name,
                        contentScale = ContentScale.Crop,
                        modifier =
                            Modifier
                                .sharedBounds(
                                    sharedTransitionScope.rememberSharedContentState(
                                        key = "image-${artist.id}"
                                    ),
                                    animatedVisibilityScope = animatedContentScope
                                ).height(200.dp)
                                .aspectRatio(1.0f)
                                .border(
                                    BorderStroke(4.dp, MaterialTheme.colorScheme.surface),
                                    CircleShape
                                ).clip(CircleShape)
                    )
                    Text(
                        text = artist.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier =
                            Modifier
                                .sharedBounds(
                                    sharedTransitionScope.rememberSharedContentState(
                                        key = "name-${artist.id}"
                                    ),
                                    animatedVisibilityScope = animatedContentScope
                                )
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text =
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non" +
                                " risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing" +
                                " nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas" +
                                " ligula massa, varius a, semper congue, euismod non, mi.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}