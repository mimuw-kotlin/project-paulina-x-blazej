package pl.edu.uw.juwenalia.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CardWithAction(
    title: String,
    subtitle: String,
    bodyText: String,
    buttonIcon: ImageVector,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    withDivider: Boolean = true,
    colors: CardColors = CardDefaults.cardColors()
) {
    Card(
        colors = colors,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = bodyText,
                style = MaterialTheme.typography.bodyMedium
            )
            if (withDivider) {
                HorizontalDivider(
                    modifier =
                        Modifier.padding(
                            vertical = 16.dp
                        )
                )
            }
            Button(onClick = onButtonClick, modifier = Modifier.align(Alignment.End)) {
                Icon(buttonIcon, contentDescription = null)
                Text(text = buttonText, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}