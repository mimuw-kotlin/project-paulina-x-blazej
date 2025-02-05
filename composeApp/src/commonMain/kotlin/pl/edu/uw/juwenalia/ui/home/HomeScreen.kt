package pl.edu.uw.juwenalia.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.app_name
import juweappka.composeapp.generated.resources.artists_section_header
import juweappka.composeapp.generated.resources.main_card_body
import juweappka.composeapp.generated.resources.main_card_button
import juweappka.composeapp.generated.resources.main_card_subtitle
import juweappka.composeapp.generated.resources.main_card_title
import juweappka.composeapp.generated.resources.news_section_header
import juweappka.composeapp.generated.resources.sponsor_logo_placeholder
import juweappka.composeapp.generated.resources.sponsors_and_partners_section_header
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pl.edu.uw.juwenalia.ui.common.CardWithAction
import pl.edu.uw.juwenalia.ui.home.components.ArtistListItem
import pl.edu.uw.juwenalia.ui.home.components.FeedSectionHeader
import pl.edu.uw.juwenalia.ui.home.components.NewsGridItem
import pl.edu.uw.juwenalia.ui.home.components.SocialMediaCard
import pl.edu.uw.juwenalia.ui.home.components.SponsorListItem

private const val BUY_TICKETS_URL = "https://www.mimuw.edu.pl/pl/"
private const val FACEBOOK_URL = "https://www.facebook.com/juwenalia.uw"
private const val INSTAGRAM_URL = "https://www.instagram.com/juwenalia.uw/"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(homeViewModel: HomeViewModel) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    val uriHandler = LocalUriHandler.current
    val density = LocalDensity.current
    val edgeToEdgeOffsetPx =
        with(density) {
            16.dp.roundToPx()
        }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(stringResource(Res.string.app_name)) })
    }) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = homeUiState.isLoading,
            onRefresh = { homeViewModel.refresh() },
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    CardWithAction(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                        title = stringResource(Res.string.main_card_title),
                        subtitle = stringResource(Res.string.main_card_subtitle),
                        bodyText = stringResource(Res.string.main_card_body),
                        buttonIcon = Icons.Filled.LocalActivity,
                        buttonText = stringResource(Res.string.main_card_button),
                        onButtonClick = { uriHandler.openUri(BUY_TICKETS_URL) }
                    )
                }

                if (homeUiState.news.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NewsGridItem(
                            news = homeUiState.news[0],
                            darkTextColor = false,
                            imageContentDescription = "",
                            onClick = { /* TODO */ }
                        )
                    }
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
                        items(homeUiState.artists.size) { i ->
                            ArtistListItem(
                                artist = homeUiState.artists[i],
                                onClick = { /* TODO */ }
                            )
                        }
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    SocialMediaCard(
                        onFacebookButtonClick = { uriHandler.openUri(FACEBOOK_URL) },
                        onInstagramButtonClick = { uriHandler.openUri(INSTAGRAM_URL) },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 8.dp)
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    FeedSectionHeader(
                        stringResource(Res.string.sponsors_and_partners_section_header)
                    )
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
                        items(homeUiState.sponsors.size) { i ->
                            SponsorListItem(
                                sponsor = homeUiState.sponsors[i],
                                onClick = { /* TODO */ }
                            )
                        }
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    FeedSectionHeader(stringResource(Res.string.news_section_header))
                }

                items(homeUiState.news) { news ->
                    NewsGridItem(
                        news = news,
                        darkTextColor = false,
                        imageContentDescription = "Zapowiedź artystów",
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}