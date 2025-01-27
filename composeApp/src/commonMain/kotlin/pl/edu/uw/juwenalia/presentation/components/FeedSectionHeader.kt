package pl.edu.uw.juwenalia.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun FeedSectionHeader(
    text: String,
    modifier: Modifier =
        Modifier.padding(
            top = 16.dp,
            bottom = 8.dp
        ),
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Text(
        text = text,
        style = textStyle,
        modifier = modifier
    )
}