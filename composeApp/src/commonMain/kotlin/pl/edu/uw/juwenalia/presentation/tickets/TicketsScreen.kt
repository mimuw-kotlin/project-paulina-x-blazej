package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.ticket_image_placeholder
import juweappka.composeapp.generated.resources.tickets_title
import juweappka.composeapp.generated.resources.upload_ticket
import juweappka.composeapp.generated.resources.your_tickets
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.presentation.components.CardGridItem
import pl.edu.uw.juwenalia.presentation.components.CardWithAction


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TicketsScreen() {
    var ticketCount by rememberSaveable { mutableIntStateOf(0) }
    val uriHandler = LocalUriHandler.current
    var showFilePicker by remember { mutableStateOf(false) }

    val fileType = listOf("pdf")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { platformFile ->
        showFilePicker = false
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = stringResource(Res.string.tickets_title)) })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = {
                ticketCount++
                showFilePicker = true
                },
            icon = { Icon(Icons.Filled.Upload, stringResource(Res.string.upload_ticket)) },
            text = { Text(text = stringResource(Res.string.upload_ticket)) }
        )
    }) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding =
                if (ticketCount == 0) {
                    PaddingValues(horizontal = 16.dp)
                } else {
                    PaddingValues(start = 16.dp, end = 16.dp, bottom = 120.dp)
                },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                CardWithAction(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    title = "1. pula biletów",
                    subtitle = "Już dostępna!",
                    bodyText =
                        "Nie zwlekaj – liczba biletów w puli jest limitowana" +
                            ", a ceny będą wzrastać.",
                    buttonIcon = Icons.Filled.ShoppingCart,
                    buttonText = "Kup teraz",
                    onButtonClick = {
                        uriHandler.openUri("https://www.mimuw.edu.pl/pl/")
                    }
                )
            }

            if (ticketCount == 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    NoTicketsEmptyState(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp)
                    )
                }
            } else {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(Res.string.your_tickets),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }

                items(ticketCount) {
                    CardGridItem(
                        title = "Bilet dwudniowy $it",
                        subtitle = "Imię nazwisko",
                        image = Res.drawable.ticket_image_placeholder,
                        imageContentDescription = "Tickets",
                        buttonIcon = Icons.Filled.Delete,
                        buttonIconContentDescription = "Usuń",
                        onCardClick = { /* TODO */ },
                        onButtonClick = { ticketCount-- }
                    )
                }
            }
        }
    }
}