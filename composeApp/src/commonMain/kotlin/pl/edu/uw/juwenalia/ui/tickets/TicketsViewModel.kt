package pl.edu.uw.juwenalia.ui.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.repository.TicketRepository

class TicketsViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            ticketRepository.tickets.collect { tickets ->
                _uiState.value = _uiState.value.copy(tickets = tickets)
            }
        }
    }

    private val _uiState = MutableStateFlow(TicketsUiState())
    val uiState: StateFlow<TicketsUiState> = _uiState.asStateFlow()

    fun saveTicket(file: PlatformFile) {
        ticketRepository.saveTicket(file)
    }

    fun deleteTicket(filename: String) {
        ticketRepository.deleteTicket(filename)
    }

    fun openTicket(filename: String) {
        val ticketBytes = ticketRepository.getTicketBytes(filename)
        _uiState.value =
            _uiState.value.copy(
                openedTicketFilename = filename,
                openedTicketBytes = ticketBytes
            )
    }

    fun dismissTicket() {
        _uiState.value = _uiState.value.copy(openedTicketFilename = null)
    }
}