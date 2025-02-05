package pl.edu.uw.juwenalia.ui.artists

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
import juweappka.composeapp.generated.resources.artists_title
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pl.edu.uw.juwenalia.ui.artists.components.ArtistList
import pl.edu.uw.juwenalia.ui.common.ArtistDetail

@Serializable
object ListDestination

@Serializable
data class DetailDestination(
    val id: Int
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ArtistsScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = ListDestination
    artistsViewModel: ArtistsViewModel = koinViewModel<ArtistsViewModel>()
) {
    val artists by artistsViewModel.artists.collectAsState()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<ListDestination> {
                ArtistList(
                    artists = artists,
                    onArtistClick = {
                        navController.navigate(DetailDestination(it.id))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
            composable<DetailDestination> { backStackEntry ->
                val destination: DetailDestination = backStackEntry.toRoute()
                ArtistDetail(
                    artist = artists[destination.id],
                    topBarTitle = stringResource(Res.string.artists_title),
                    onBackClick = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}