package pl.edu.uw.juwenalia.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Test screen") })
    }) { innerPadding ->
        Column(
            modifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding)
        ) {
        }
    }
}