package pl.edu.uw.juwenalia.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.repository.NewsRepository

class HomeViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            newsRepository.newsStream.collect {
                _uiState.value = _uiState.value.copy(news = it)
            }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            newsRepository.refresh()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}