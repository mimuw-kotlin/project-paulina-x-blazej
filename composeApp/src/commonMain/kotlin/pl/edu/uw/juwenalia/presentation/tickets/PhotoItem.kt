package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.core.PlatformFile

@Composable
fun PhotoItem(
    file: PlatformFile
) {
    var bytes by remember(file) { mutableStateOf<ByteArray?>(null) }
    var showName by remember { mutableStateOf(false) }

    LaunchedEffect(file) {
        bytes = if (file.supportsStreams()) {
            val size = file.getSize()
            if (size != null && size > 0L) {
                val buffer = ByteArray(size.toInt())
                val tmpBuffer = ByteArray(1000)
                var totalBytesRead = 0
                file.getStream().use {
                    while (it.hasBytesAvailable()) {
                        val numRead = it.readInto(tmpBuffer, 1000)
                        tmpBuffer.copyInto(
                            buffer,
                            destinationOffset = totalBytesRead,
                            endIndex = numRead,
                        )
                        totalBytesRead += numRead
                    }
                }
                buffer
            } else {
                file.readBytes()
            }
        } else {
            file.readBytes()
        }
    }

    Surface(
        onClick = { showName = !showName },
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.medium)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            bytes?.let {
                AsyncImage(
                    bytes,
                    contentDescription = file.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            AnimatedVisibility(
                visible = showName,
                modifier = Modifier.padding(4.dp).align(Alignment.BottomStart)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(
                        file.name,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}