package pl.edu.uw.juwenalia.presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GpsNotFixed
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.festival_place
import juweappka.composeapp.generated.resources.ticket_image_placeholder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.presentation.components.FullscreenSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MapScreen() {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var searchExpanded by rememberSaveable { mutableStateOf(false) }
    val chipScrollState = rememberScrollState()
    val selectedChips = rememberSaveable { mutableStateOf(setOf<Int>()) }

    val density = LocalDensity.current

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = !searchExpanded,
                enter = fadeIn() + slideInVertically { with(density) { 112.dp.roundToPx() } },
                exit = fadeOut() + slideOutVertically { with(density) { 112.dp.roundToPx() } }
            ) {
                Box {
                    ExtendedFloatingActionButton(
                        onClick = { /* TODO */ },
                        icon = {
                            Icon(
                                Icons.Filled.Place,
                                stringResource(Res.string.festival_place)
                            )
                        },
                        text = { Text(text = stringResource(Res.string.festival_place)) },
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                    SmallFloatingActionButton(
                        onClick = { /* TODO */ },
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 72.dp)
                    ) {
                        Icon(Icons.Filled.GpsNotFixed, null)
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image( // TODO: to be swapped with a MapView
                painter = painterResource(Res.drawable.ticket_image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .wrapContentHeight()
            ) {
                val animatedPadding by animateDpAsState(
                    if (searchExpanded) {
                        0.dp
                    } else {
                        16.dp
                    }
                )

                FullscreenSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { /* do nothing */ },
                    expanded = searchExpanded,
                    onExpandedChange = { searchExpanded = it },
                    onFocus = { searchExpanded = !searchExpanded },
                    onQueryClear = { searchQuery = "" },
                    searchContent = { Text("Search results goes here") },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = animatedPadding)
                )

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .horizontalScroll(chipScrollState)
                            .padding(16.dp)
                ) {
                    for (i in 1..5) {
                        val isSelected = selectedChips.value.contains(i)
                        ElevatedFilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedChips.value =
                                    if (isSelected) {
                                        selectedChips.value - i
                                    } else {
                                        selectedChips.value + i
                                    }
                            },
                            label = { Text("Chip $i") },
                            leadingIcon = {
                                if (isSelected) {
                                    Icon(Icons.Filled.Check, null)
                                } else {
                                    Icon(Icons.Filled.Place, null)
                                }
                            },
                            modifier = Modifier.padding(end = 8.dp).height(36.dp),
                            colors =
                                FilterChipDefaults.elevatedFilterChipColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    iconColor = MaterialTheme.colorScheme.onSurface
                                ),
                            shape = CircleShape
                        )
                    }
                }
            }
        }
    }
}