package pl.edu.uw.juwenalia.data.repository

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.file.deleteFile
import pl.edu.uw.juwenalia.data.file.getFileBytesByName
import pl.edu.uw.juwenalia.data.file.getFiles
import pl.edu.uw.juwenalia.data.file.savePickedFile

private const val TICKETS_FOLDER_NAME = "ticket_resources"

class DefaultTicketRepository : TicketRepository {

    private val _tickets: MutableStateFlow<List<String>> =
        MutableStateFlow(getFiles(TICKETS_FOLDER_NAME))
    override val tickets = _tickets.asStateFlow()

    override fun saveTicket(file: PlatformFile) {
        CoroutineScope(Dispatchers.IO).launch {
            savePickedFile(TICKETS_FOLDER_NAME, file)
            updateTickets()
        }
    }

    override fun getTicketBytes(filename: String): ByteArray? =
        getFileBytesByName(TICKETS_FOLDER_NAME, filename)

    override fun deleteTicket(filename: String) {
        deleteFile(TICKETS_FOLDER_NAME, filename)
        updateTickets()
    }

    private fun updateTickets() {
        _tickets.value = getFiles(TICKETS_FOLDER_NAME)
    }
}