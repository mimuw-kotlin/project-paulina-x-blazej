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
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

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
) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
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
                val detailDestination: DetailDestination = backStackEntry.toRoute()
                ArtistDetailScreen(
                    artist = artists[detailDestination.id],
                    onBackClick = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}