package pl.edu.uw.juwenalia.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.app_name
import juweappka.composeapp.generated.resources.artist_photo_placeholder
import juweappka.composeapp.generated.resources.artists_section_header
import juweappka.composeapp.generated.resources.news_section_header
import juweappka.composeapp.generated.resources.sponsor_logo_placeholder
import juweappka.composeapp.generated.resources.sponsors_and_partners_section_header
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.data.ArtistData
import pl.edu.uw.juwenalia.data.NewsData
import pl.edu.uw.juwenalia.data.getAppFilesDirectory
import pl.edu.uw.juwenalia.data.getArtists
import pl.edu.uw.juwenalia.data.getNews
import pl.edu.uw.juwenalia.ui.common.CardWithAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    val uriHandler = LocalUriHandler.current
    val localFileDir = getAppFilesDirectory()
    var newsData: List<NewsData> by remember { mutableStateOf(emptyList()) }
    var artistData: List<ArtistData> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) {
        newsData = getNews(localFileDir)
        artistData = getArtists(localFileDir)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(stringResource(Res.string.app_name)) })
    }) { innerPadding ->

        val density = LocalDensity.current
        val edgeToEdgeOffsetPx =
            with(density) {
                16.dp.roundToPx()
            }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            modifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                CardWithAction(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    title = "jUWenalia nadchodzą!",
                    subtitle = "Widzimy się już 9 i 10 maja!",
                    bodyText =
                        "Najgłośniejszy studencki festiwal w Warszawie powraca " +
                            "w 2025 roku! Bilety już dostępne – kup teraz " +
                            "i zaoszczędź nawet do 50%.",
                    buttonIcon = Icons.Filled.LocalActivity,
                    buttonText = "Zgarnij bilety",
                    onButtonClick = { uriHandler.openUri("https://www.mimuw.edu.pl/pl/") }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                NewsGridItem(
                    title = "Dawid Podsiadło na jUWenaliach 2025!",
                    darkTextColor = false,
                    image = Res.drawable.artist_photo_placeholder,
                    imageContentDescription = "Zapowiedź artystów",
                    onClick = { /* TODO */ }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                FeedSectionHeader(stringResource(Res.string.artists_section_header))
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier =
                        Modifier.layout { measurable, constraints ->
                            val looseConstraints = constraints.offset(edgeToEdgeOffsetPx * 2, 0)
                            val placeable = measurable.measure(looseConstraints)
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(0, 0)
                            }
                        }
                ) {
                    items(artistData.size) { i ->
                        ArtistListItem(
                            name = artistData[i].name,
                            filesDir = localFileDir,
                            fileName = artistData[i].image,
                            onClick = { /* TODO */ }
                        )
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                SocialMediaCard(
                    onFacebookButtonClick = {
                        uriHandler.openUri(
                            "https://www.facebook.com/juwenalia.uw"
                        )
                    },
                    onInstagramButtonClick = {
                        uriHandler.openUri(
                            "https://www.instagram.com/juwenalia.uw/"
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 8.dp)
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                FeedSectionHeader(stringResource(Res.string.sponsors_and_partners_section_header))
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier =
                        Modifier.layout { measurable, constraints ->
                            val looseConstraints = constraints.offset(edgeToEdgeOffsetPx * 2, 0)
                            val placeable = measurable.measure(looseConstraints)
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(0, 0)
                            }
                        }
                ) {
                    items(10) {
                        SponsorListItem(
                            name = "Perła",
                            image = Res.drawable.sponsor_logo_placeholder,
                            onClick = { /* TODO */ }
                        )
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                FeedSectionHeader(stringResource(Res.string.news_section_header))
            }

            items(newsData) { news ->
                NewsGridItem(
                    title = news.title,
                    darkTextColor = false,
                    image = Res.drawable.artist_photo_placeholder,
                    filesDir = localFileDir,
                    fileName = news.image,
                    imageContentDescription = "Zapowiedź artystów",
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}