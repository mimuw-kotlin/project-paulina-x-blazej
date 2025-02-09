package pl.edu.uw.juwenalia.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.repository.FeedRepository

class ArtistsViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _artists = MutableStateFlow(emptyList<Artist>())
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    init {
        viewModelScope.launch {
            feedRepository.artists.collect {
                _artists.value = it
            }
        }
    }
}