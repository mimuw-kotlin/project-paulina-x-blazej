package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.core.PlatformFile

@Composable
internal fun PhotoItem(file: PlatformFile) {
    var bytes by remember(file) { mutableStateOf<ByteArray?>(null) }

    LaunchedEffect(file) {
        bytes =
            if (file.supportsStreams()) {
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
                                endIndex = numRead
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

    bytes?.let {
        AsyncImage(
            bytes,
            contentDescription = file.name,
            contentScale = ContentScale.Fit,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
        )
    }
}