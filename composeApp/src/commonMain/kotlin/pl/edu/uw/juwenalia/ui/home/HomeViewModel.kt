package pl.edu.uw.juwenalia.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.repository.FeedRepository

class HomeViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refresh()
        viewModelScope.launch {
            feedRepository.newsStream.collect {
                _uiState.value = _uiState.value.copy(news = it)
            }
        }
        viewModelScope.launch {
            feedRepository.artistStream.collect {
                _uiState.value = _uiState.value.copy(artists = it)
            }
        }
        viewModelScope.launch {
            feedRepository.sponsorStream.collect {
                _uiState.value = _uiState.value.copy(sponsors = it)
            }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            feedRepository.refresh()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}