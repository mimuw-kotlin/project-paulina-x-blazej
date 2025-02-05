
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.edu.uw.juwenalia.data.repository.TicketRepository
import pl.edu.uw.juwenalia.ui.tickets.TicketsScreen
import pl.edu.uw.juwenalia.ui.tickets.TicketsViewModel
import kotlin.test.Test

class TestTicketRepository : TicketRepository {
    private val _tickets = MutableStateFlow<List<String>>(emptyList())
    override val tickets = _tickets.asStateFlow()

    private val ticketStorage = mutableMapOf<String, ByteArray>()

    override suspend fun saveTicket(file: PlatformFile?) {
        val filename = "ticket.png"
        val fileBytes = byteArrayOf(1, 2, 3)
        ticketStorage[filename] = fileBytes
        _tickets.value = ticketStorage.keys.toList()
    }

    override fun getTicketBytes(filename: String): ByteArray? = ticketStorage[filename]

    override fun deleteTicket(filename: String) {
        ticketStorage.remove(filename)
        _tickets.value = ticketStorage.keys.toList()
    }
}

class TicketsUiTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun addAndDeleteTicketTest() =
        runComposeUiTest {
            val testTicketRepository = TestTicketRepository()
            val viewModel = TicketsViewModel(testTicketRepository)

            setContent {
                TicketsScreen(viewModel)
            }

            onNodeWithTag("ticket_card").assertDoesNotExist()
            viewModel.saveTicket(null) // Platform file doesn't have public constructor.
            onAllNodesWithTag("ticket_card").assertCountEquals(1)
            onNodeWithTag("ticket_remove_button").performClick()
            onNodeWithTag("ticket_card").assertDoesNotExist()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun openTicketTest() =
        runComposeUiTest {
            val testTicketRepository = TestTicketRepository()
            val viewModel = TicketsViewModel(testTicketRepository)

            setContent {
                TicketsScreen(viewModel)
            }

            viewModel.saveTicket(null) // Platform file doesn't have public constructor.
            onNodeWithTag("ticket_card").performClick()
            onNodeWithTag("ticket_modal").assertExists()
        }
}