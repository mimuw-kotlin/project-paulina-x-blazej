package pl.edu.uw.juwenalia.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.edu.uw.juwenalia.data.model.Sponsor

@Composable
internal fun SponsorListItem(
    sponsor: Sponsor,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier =
                Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            AsyncImage(
                sponsor.imageByteArray,
                contentDescription = sponsor.name,
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
            )
        }
        Text(
            text = sponsor.name,
            style = MaterialTheme.typography.titleSmall
        )
    }
}