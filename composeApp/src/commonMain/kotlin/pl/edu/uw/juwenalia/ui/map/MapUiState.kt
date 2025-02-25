package pl.edu.uw.juwenalia.ui.map

import dev.sargunv.maplibrecompose.expressions.ast.Expression
import dev.sargunv.maplibrecompose.expressions.dsl.nil
import dev.sargunv.maplibrecompose.expressions.value.BooleanValue
import io.github.dellisd.spatialk.geojson.Position
import pl.edu.uw.juwenalia.data.model.GpsState
import pl.edu.uw.juwenalia.data.model.MapPoint
import pl.edu.uw.juwenalia.data.model.MapPointCategory

data class MapUiState(
    val searchQuery: String = "",
    val isSearchExpanded: Boolean = false,
    val searchResults: List<MapPoint> = emptyList(),
    val selectedFilters: Set<MapPointCategory> = emptySet(),
    val mapFilter: Expression<BooleanValue> = nil(),
    val gpsState: GpsState = GpsState.NOT_FIXED,
    val userPosition: Position? = null,
    val selectedMapPoint: MapPoint? = null
)