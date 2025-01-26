package pl.edu.uw.juwenalia.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
fun CardGridItem(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    title: String,
    subtitle: String,
    image: DrawableResource,
    imageContentDescription: String,
    buttonIcon: ImageVector,
    buttonIconContentDescription: String,
    onCardClick: () -> Unit,
    onButtonClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier.clickable(onClick = onCardClick),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box {
            Column {
                Image(
                    bitmap = imageResource(image),
                    contentDescription = imageContentDescription,
                    modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.FillWidth,
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            FilledIconButton(
                onClick = onButtonClick, modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            ) {
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = buttonIconContentDescription,
                )
            }
        }
    }
}