package pl.edu.uw.juwenalia.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.social_media_card_body
import juweappka.composeapp.generated.resources.social_media_card_title
import juweappka.composeapp.generated.resources.social_media_facebook
import juweappka.composeapp.generated.resources.social_media_instagram
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SocialMediaCard(
    onFacebookButtonClick: () -> Unit,
    onInstagramButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.social_media_card_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(Res.string.social_media_card_body),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row {
                Button(
                    onClick = onFacebookButtonClick,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = stringResource(Res.string.social_media_facebook))
                }
                Button(
                    onClick = onInstagramButtonClick,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                ) {
                    Text(text = stringResource(Res.string.social_media_instagram))
                }
            }
        }
    }
}