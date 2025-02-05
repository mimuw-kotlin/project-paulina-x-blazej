import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor
import pl.edu.uw.juwenalia.data.repository.FeedRepository
import pl.edu.uw.juwenalia.ui.artists.ArtistsScreen
import pl.edu.uw.juwenalia.ui.artists.ArtistsViewModel
import kotlin.test.Test

private val testArtists = listOf(
    Artist(
        id = 0, name = "Artist 1",
        imageFilename = "artist1.jpg",
        day = 1,
        description = "",
        time = "20:00",
        imageByteArray = ByteArray(0)),
    Artist(
        id = 1,
        name = "Artist 2",
        imageFilename = "artist2.jpg",
        day = 1,
        description = "",
        time = "21:00",
        imageByteArray = ByteArray(0))
)

class FeedRepositoryMock : FeedRepository {
    override val news: Flow<List<News>>
        get() = flow {emit(emptyList())}
    override val artists: Flow<List<Artist>>
        get() = flow {emit(testArtists)}
    override val sponsors: Flow<List<Sponsor>>
        get() = flow {emit(emptyList())}

    override suspend fun refresh() {}
}

class ArtistsUiTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun artistDetailScreenTest() =
        runComposeUiTest {
            setContent {
                val navController = rememberNavController()
                ArtistsScreen(
                    navController = navController,
                    artistsViewModel = ArtistsViewModel(feedRepository = FeedRepositoryMock())
                )
            }

            onNodeWithTag("ArtistList")
                .onChildAt(0)
                .performClick()

            onNodeWithTag("artistDetail-0").assertIsDisplayed()
        }

}