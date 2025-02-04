package pl.edu.uw.juwenalia.ui.map.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material.icons.outlined.GpsNotFixed
import androidx.compose.material.icons.outlined.GpsOff
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrecompose.compose.rememberCameraState
import dev.sargunv.maplibrecompose.compose.rememberStyleState
import dev.sargunv.maplibrecompose.core.CameraMoveReason
import dev.sargunv.maplibrecompose.core.CameraPosition
import dev.sargunv.maplibrecompose.material3.controls.AttributionButton
import io.github.dellisd.spatialk.geojson.Position
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.fix_gps
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pl.edu.uw.juwenalia.data.model.GpsState
import pl.edu.uw.juwenalia.data.source.StaticMapData.FESTIVAL_PLACE_DEFAULT_ZOOM
import pl.edu.uw.juwenalia.data.source.StaticMapData.FESTIVAL_PLACE_LATITUDE
import pl.edu.uw.juwenalia.data.source.StaticMapData.FESTIVAL_PLACE_LONGITUDE
import pl.edu.uw.juwenalia.data.source.StaticMapData.MAP_POINT_FOCUS_ZOOM
import pl.edu.uw.juwenalia.ui.map.MapView
import pl.edu.uw.juwenalia.ui.map.MapViewModel
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MapScreen() {
    val mapViewModel = koinViewModel<MapViewModel>()
    val mapUiState by mapViewModel.uiState.collectAsState()

    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val cameraState =
        rememberCameraState(
            firstPosition =
                CameraPosition(
                    target =
                        Position(
                            latitude = FESTIVAL_PLACE_LATITUDE,
                            longitude = FESTIVAL_PLACE_LONGITUDE
                        ),
                    zoom = FESTIVAL_PLACE_DEFAULT_ZOOM
                )
        )
    val styleState = rememberStyleState()

    val bottomSheetState =
        rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            confirmValueChange = {
                if (it == SheetValue.Hidden) {
                    mapViewModel.clearSelectedMapPoint()
                }
                true
            },
            skipHiddenState = false
        )

    LaunchedEffect(mapUiState.selectedMapPoint) {
        if (mapUiState.selectedMapPoint == null) {
            bottomSheetState.hide()
        } else {
            launch {
                cameraState.animateTo(
                    finalPosition =
                        cameraState.position.copy(
                            target = mapUiState.selectedMapPoint!!.position,
                            zoom = MAP_POINT_FOCUS_ZOOM
                        ),
                    duration = 0.5.seconds
                )
            }
            launch { bottomSheetState.expand() }
        }
    }

    BottomSheetScaffold(
        sheetContent =
            if (mapUiState.selectedMapPoint == null) {
                { /* empty sheet */ }
            } else {
                {
                    MapPointDetails(
                        mapPoint = mapUiState.selectedMapPoint!!,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
            },
        scaffoldState =
            rememberBottomSheetScaffoldState(
                bottomSheetState = bottomSheetState
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LaunchedEffect(cameraState.moveReason) {
                if (mapUiState.gpsState == GpsState.FIXED &&
                    cameraState.moveReason == CameraMoveReason.GESTURE
                ) {
                    mapViewModel.unfixGpsPosition()
                }
            }

            MapView(
                cameraState = cameraState,
                styleState = styleState,
                mapPointsFilter = mapUiState.mapFilter,
                onMapPointClick = { mapViewModel.selectMapPoint(it) },
                gpsPosition = mapUiState.userPosition
            )

            AttributionButton(styleState, modifier = Modifier.align(Alignment.BottomStart))

            MapFloatingActionButtons(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                visible = !mapUiState.isSearchExpanded,
                enterTransition =
                    fadeIn() + slideInVertically { with(density) { 112.dp.roundToPx() } },
                exitTransition =
                    fadeOut() + slideOutVertically { with(density) { 112.dp.roundToPx() } },
                cameraState = cameraState,
                fixGpsButtonIcon = {
                    when (mapUiState.gpsState) {
                        GpsState.FIXED ->
                            Icon(
                                Icons.Outlined.GpsFixed,
                                stringResource(Res.string.fix_gps)
                            )

                        GpsState.LOADING ->
                            Icon(
                                Icons.Outlined.HourglassTop,
                                stringResource(Res.string.fix_gps)
                            )

                        GpsState.UNAVAILABLE ->
                            Icon(
                                Icons.Outlined.GpsOff,
                                stringResource(Res.string.fix_gps)
                            )

                        GpsState.NOT_FIXED ->
                            Icon(
                                Icons.Outlined.GpsNotFixed,
                                stringResource(Res.string.fix_gps)
                            )
                    }
                },
                onFixGpsClick = {
                    mapViewModel.fixGpsPosition {
                        coroutineScope.launch {
                            cameraState.animateTo(
                                finalPosition =
                                    cameraState.position.copy(
                                        target = it,
                                        zoom = FESTIVAL_PLACE_DEFAULT_ZOOM
                                    ),
                                duration = 1.seconds
                            )
                        }
                    }
                },
                festivalAreaButtonExpanded = mapUiState.gpsState == GpsState.FIXED,
                onFestivalAreaClick = {
                    mapViewModel.unfixGpsPosition()
                    coroutineScope.launch {
                        cameraState.animateTo(
                            finalPosition =
                                cameraState.position.copy(
                                    target =
                                        Position(
                                            latitude = FESTIVAL_PLACE_LATITUDE,
                                            longitude = FESTIVAL_PLACE_LONGITUDE
                                        ),
                                    zoom = FESTIVAL_PLACE_DEFAULT_ZOOM
                                ),
                            duration = 1.seconds
                        )
                    }
                }
            )

            MapTopBar(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                searchExpanded = mapUiState.isSearchExpanded,
                onSearchExpandedChange = { mapViewModel.toggleSearchBar(it) },
                searchQuery = mapUiState.searchQuery,
                onSearchQueryChange = { mapViewModel.updateSearchQuery(it) },
                onSearchQueryClear = { mapViewModel.updateSearchQuery("") },
                onSearchLeadingButtonClick = { mapViewModel.toggleSearchBar() },
                searchResults = mapUiState.searchResults,
                onSearchResultClick = { mapViewModel.selectSearchQuery(it) },
                selectedFilters = mapUiState.selectedFilters,
                onFilterClick = { mapViewModel.toggleFilter(it) }
            )
        }
    }
}