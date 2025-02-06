package pl.edu.uw.juwenalia.ui.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.app_name
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pl.edu.uw.juwenalia.ui.common.ArtistDetail
import pl.edu.uw.juwenalia.ui.home.components.Feed
import pl.edu.uw.juwenalia.ui.home.components.NewsDetail

@Serializable
object FeedDestination

@Serializable
data class NewsDetailDestination(
    val id: Int
)

@Serializable
data class ArtistDetailDestination(
    val id: Int
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    navController: NavHostController = rememberNavController(),
    startDestination: Any = FeedDestination
) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<FeedDestination> {
                Feed(
                    news = homeUiState.news,
                    onNewsClick = {
                        navController.navigate(NewsDetailDestination(it.id))
                    },
                    artists = homeUiState.artists,
                    onArtistClick = {
                        navController.navigate(ArtistDetailDestination(it.id))
                    },
                    sponsors = homeUiState.sponsors,
                    isLoading = homeUiState.isLoading,
                    onRefresh = homeViewModel::refresh,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
            composable<NewsDetailDestination> { backStackEntry ->
                val destination: NewsDetailDestination = backStackEntry.toRoute()
                NewsDetail(
                    news = homeUiState.news[destination.id],
                    onBackClick = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
            composable<ArtistDetailDestination> { backStackEntry ->
                val destination: ArtistDetailDestination = backStackEntry.toRoute()
                ArtistDetail(
                    artist = homeUiState.artists[destination.id],
                    topBarTitle = stringResource(Res.string.app_name),
                    onBackClick = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}