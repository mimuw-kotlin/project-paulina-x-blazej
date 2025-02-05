package pl.edu.uw.juwenalia.data.repository

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    val tickets: Flow<List<String>>

    suspend fun saveTicket(file: PlatformFile)

    fun getTicketBytes(filename: String): ByteArray?

    fun deleteTicket(filename: String)
}