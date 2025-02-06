
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor
import pl.edu.uw.juwenalia.data.repository.FeedRepository
import pl.edu.uw.juwenalia.ui.artists.ArtistsScreen
import pl.edu.uw.juwenalia.ui.artists.ArtistsViewModel
import kotlin.test.Test

class FeedRepositoryMock : FeedRepository {
    private val _artists =
        MutableStateFlow(
            listOf(
                Artist(
                    id = 0,
                    name = "Artist 1",
                    imageFilename = "artist1.jpg",
                    day = 1,
                    description = "",
                    time = "20:00",
                    imageByteArray = ByteArray(0)
                ),
                Artist(
                    id = 1,
                    name = "Artist 2",
                    imageFilename = "artist2.jpg",
                    day = 1,
                    description = "",
                    time = "21:00",
                    imageByteArray = ByteArray(0)
                )
            )
        )
    override val artists: Flow<List<Artist>> = _artists.asStateFlow()

    override val news: Flow<List<News>> = flow { emit(emptyList()) }
    override val sponsors: Flow<List<Sponsor>> = flow { emit(emptyList()) }

    override suspend fun refresh() {}

    fun addArtist(artist: Artist) {
        _artists.update { currentList ->
            currentList + artist
        }
    }
}

class ArtistsUiTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun viewArtistListTest() =
        runComposeUiTest {
            val feedRepository = FeedRepositoryMock()
            val viewModel = ArtistsViewModel(feedRepository)

            setContent {
                ArtistsScreen(
                    artistsViewModel = viewModel
                )
            }

            // There should be exactly two artists in the list
            onNodeWithTag("artist_item_0").assertExists()
            onNodeWithTag("artist_item_1").assertExists()
            onNodeWithTag("artist_item_2").assertDoesNotExist()

            // Names and play times should be displayed for each artist
            onNodeWithText("Artist 1").assertExists()
            onNodeWithText("Artist 2").assertExists()
            onNodeWithText("20:00").assertExists()
            onNodeWithText("21:00").assertExists()

            // Updates to the artist list should be handled immediately
            feedRepository.addArtist(
                Artist(
                    id = 2,
                    name = "Artist 3",
                    imageFilename = "artist3.jpg",
                    day = 1,
                    description = "",
                    time = "22:00",
                    imageByteArray = ByteArray(0)
                )
            )
            onNodeWithTag("artist_item_2").assertExists()
            onNodeWithTag("artist_item_3").assertDoesNotExist()
            onNodeWithText("Artist 3").assertExists()
            onNodeWithText("22:00").assertExists()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun artistDetailScreenTest() =
        runComposeUiTest {
            val feedRepository = FeedRepositoryMock()
            val viewModel = ArtistsViewModel(feedRepository)

            setContent {
                ArtistsScreen(
                    artistsViewModel = viewModel
                )
            }

            onNodeWithTag("artist_item_0").performClick()
            onNodeWithTag("artist_detail_0").assertExists()
        }
}