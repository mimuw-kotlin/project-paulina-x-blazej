package pl.edu.uw.juwenalia.ui.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerType
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.delete
import juweappka.composeapp.generated.resources.ticket_image_content_desc
import juweappka.composeapp.generated.resources.ticket_image_placeholder
import juweappka.composeapp.generated.resources.ticket_item_title
import juweappka.composeapp.generated.resources.tickets_title
import juweappka.composeapp.generated.resources.upload_ticket
import juweappka.composeapp.generated.resources.your_tickets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.data.FolderEnum
import pl.edu.uw.juwenalia.data.deleteFile
import pl.edu.uw.juwenalia.data.getAppFilesDirectory
import pl.edu.uw.juwenalia.data.getFileSet
import pl.edu.uw.juwenalia.data.savePickedFile
import pl.edu.uw.juwenalia.ui.common.CardGridItem
import pl.edu.uw.juwenalia.ui.common.CardWithAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TicketsScreen() {
    val uriHandler = LocalUriHandler.current

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var selectedFile: String by remember { mutableStateOf("") }
    val localFileDir = getAppFilesDirectory()
    var fileNamesSet: Set<String> by remember {
        mutableStateOf(getFileSet(localFileDir, FolderEnum.TICKET_RESOURCES))
    }

    val ticketFilePicker =
        rememberFilePickerLauncher(
            type = PickerType.File(listOf("png")),
            onResult = { file ->
                file?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        savePickedFile(localFileDir, it)
                    }
                    fileNamesSet += it.name
                }
            }
        )

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(Res.string.tickets_title)) }
        )
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = { ticketFilePicker.launch() },
            icon = { Icon(Icons.Filled.Upload, stringResource(Res.string.upload_ticket)) },
            text = { Text(text = stringResource(Res.string.upload_ticket)) }
        )
    }) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding =
                if (fileNamesSet.isEmpty()) {
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
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    title = "Pierwsza pula biletów",
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

            if (fileNamesSet.isEmpty()) {
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
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(fileNamesSet.toList()) { file ->

                    CardGridItem(
                        title = stringResource(Res.string.ticket_item_title),
                        subtitle = file,
                        image = Res.drawable.ticket_image_placeholder,
                        imageContentDescription =
                            stringResource(
                                Res.string.ticket_image_content_desc
                            ),
                        buttonIcon = Icons.Filled.Delete,
                        buttonIconContentDescription = stringResource(Res.string.delete),
                        onCardClick = {
                            selectedFile = file
                            showBottomSheet = true
                        },
                        onButtonClick = {
                            deleteFile(localFileDir, FolderEnum.TICKET_RESOURCES, file)
                            fileNamesSet = fileNamesSet - file
                        }
                    )
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            PhotoItem(localFileDir, selectedFile)
        }
    }
}