package pl.edu.uw.juwenalia.ui.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrecompose.compose.CameraState
import dev.sargunv.maplibrecompose.material3.controls.DisappearingCompassButton
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.festival_place
import org.jetbrains.compose.resources.stringResource

@Composable
fun MapFloatingActionButtons(
    cameraState: CameraState,
    fixGpsButtonIcon: @Composable () -> Unit,
    onFixGpsClick: () -> Unit,
    festivalAreaButtonExpanded: Boolean,
    onFestivalAreaClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn() + slideInVertically { with(density) { 112.dp.roundToPx() } },
        exit = fadeOut() + slideOutVertically { with(density) { 112.dp.roundToPx() } }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            DisappearingCompassButton(
                cameraState = cameraState,
                colors =
                    ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceBright
                    )
            )
            SmallFloatingActionButton(
                onClick = onFixGpsClick,
                containerColor = MaterialTheme.colorScheme.surfaceBright,
                contentColor = MaterialTheme.colorScheme.onSurface,
                content = fixGpsButtonIcon
            )
            ExtendedFloatingActionButton(
                onClick = onFestivalAreaClick,
                icon = {
                    Icon(
                        Icons.Outlined.Place,
                        stringResource(Res.string.festival_place)
                    )
                },
                text = { Text(text = stringResource(Res.string.festival_place)) },
                expanded = festivalAreaButtonExpanded
            )
        }
    }
}