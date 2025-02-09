package pl.edu.uw.juwenalia.ui.artists.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.artists_title
import juweappka.composeapp.generated.resources.friday
import juweappka.composeapp.generated.resources.may_10
import juweappka.composeapp.generated.resources.may_9
import juweappka.composeapp.generated.resources.saturday
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.data.model.Artist

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun ArtistList(
    artists: List<Artist>,
    onArtistClick: (Artist) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val density = LocalDensity.current
    val edgeToEdgeOffsetPx =
        with(density) {
            16.dp.roundToPx()
        }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.artists_title)) }
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            contentPadding =
                PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ArtistsSectionHeader(
                    title = stringResource(Res.string.friday),
                    subtitle = stringResource(Res.string.may_9)
                )
            }

            items(artists.filter { it.day == 1 }) {
                ArtistItem(
                    artist = it,
                    onClick = { onArtistClick(it) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                HorizontalDivider(
                    modifier =
                        Modifier.layout { measurable, constraints ->
                            val looseConstraints = constraints.offset(edgeToEdgeOffsetPx * 2, 0)
                            val placeable = measurable.measure(looseConstraints)
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(0, 0)
                            }
                        }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                ArtistsSectionHeader(
                    title = stringResource(Res.string.saturday),
                    subtitle = stringResource(Res.string.may_10),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(artists.filter { it.day == 2 }) {
                ArtistItem(
                    artist = it,
                    onClick = { onArtistClick(it) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }
    }
}