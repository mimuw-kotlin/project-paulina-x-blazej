package pl.edu.uw.juwenalia.presentation.artists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ArtistsScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Artists Screen!")
    }
}