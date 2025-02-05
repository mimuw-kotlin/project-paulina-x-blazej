package pl.edu.uw.juwenalia.ui.artists

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.edu.uw.juwenalia.data.model.Artist

class ArtistsViewModel : ViewModel() {
    private val tempArtistList =
        List(10) {
            Artist(
                id = it,
                name = "Dawid Podsiadło $it",
                imageFilename = "dawid.jpg",
                imageByteArray = ByteArray(0)
            )
        }

    private val _artists = MutableStateFlow(tempArtistList)
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    // TODO: add fetching artists
}