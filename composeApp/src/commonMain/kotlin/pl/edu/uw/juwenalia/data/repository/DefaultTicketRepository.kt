package pl.edu.uw.juwenalia.data.repository

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.file.deleteFile
import pl.edu.uw.juwenalia.data.file.getAppFilesDirectory
import pl.edu.uw.juwenalia.data.file.getFileBytesByName
import pl.edu.uw.juwenalia.data.file.getFiles
import pl.edu.uw.juwenalia.data.file.savePickedFile

private const val TICKETS_FOLDER_NAME = "ticket_resources"

class DefaultTicketRepository : TicketRepository {
    private val localFileDir = getAppFilesDirectory()

    private val _tickets = MutableSharedFlow<List<String>>(replay = 1)
    override val tickets: Flow<List<String>> = _tickets.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _tickets.emit(getFiles(localFileDir, TICKETS_FOLDER_NAME))
        }
    }

    override fun saveTicket(file: PlatformFile) {
        CoroutineScope(Dispatchers.IO).launch {
            savePickedFile(localFileDir, TICKETS_FOLDER_NAME, file)
            _tickets.emit(getFiles(localFileDir, TICKETS_FOLDER_NAME))
        }
    }

    override fun getTicketBytes(filename: String): ByteArray? =
        getFileBytesByName(localFileDir, TICKETS_FOLDER_NAME, filename)

    override fun deleteTicket(filename: String) {
        deleteFile(localFileDir, TICKETS_FOLDER_NAME, filename)
        CoroutineScope(Dispatchers.IO).launch {
            _tickets.emit(getFiles(localFileDir, TICKETS_FOLDER_NAME))
        }
    }
}