package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.core.PlatformFile
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.tickets_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TicketsViewScreen(ticket : PlatformFile) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(Res.string.tickets_title)) }
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                PhotoItem(ticket)
            }
        }
    }
}