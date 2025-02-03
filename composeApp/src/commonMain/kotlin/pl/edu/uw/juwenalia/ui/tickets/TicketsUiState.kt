package pl.edu.uw.juwenalia.ui.tickets

data class TicketsUiState(
    val tickets: List<String> = emptyList(),
    val openedTicketFilename: String? = null,
    val openedTicketBytes: ByteArray? = null
)