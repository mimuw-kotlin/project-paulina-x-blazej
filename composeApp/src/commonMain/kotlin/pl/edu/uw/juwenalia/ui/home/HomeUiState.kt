package pl.edu.uw.juwenalia.ui.home

import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor

data class HomeUiState(
    val news: List<News> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val sponsors: List<Sponsor> = emptyList(),
    val isLoading: Boolean = false
)