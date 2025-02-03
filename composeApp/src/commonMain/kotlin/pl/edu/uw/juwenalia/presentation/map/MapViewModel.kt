package pl.edu.uw.juwenalia.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.sargunv.maplibrecompose.expressions.dsl.any
import dev.sargunv.maplibrecompose.expressions.dsl.asString
import dev.sargunv.maplibrecompose.expressions.dsl.const
import dev.sargunv.maplibrecompose.expressions.dsl.eq
import dev.sargunv.maplibrecompose.expressions.dsl.feature
import dev.sargunv.maplibrecompose.expressions.dsl.nil
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.uw.juwenalia.data.model.MapPoint
import pl.edu.uw.juwenalia.data.model.MapPointCategory
import pl.edu.uw.juwenalia.data.repository.MapPointRepository

class MapViewModel(
    private val mapPointRepository: MapPointRepository,
    private val geolocator: Geolocator
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun toggleSearchBar(toState: Boolean? = null) {
        _uiState.value =
            _uiState.value.copy(
                isSearchExpanded = toState ?: !_uiState.value.isSearchExpanded,
                selectedMapPoint = null
            )
    }

    fun fixGpsPosition(onLocationReceived: (Position) -> Unit) {
        _uiState.value = _uiState.value.copy(gpsState = GpsState.LOADING)
        viewModelScope.launch {
            val location =
                geolocator
                    .current(Priority.HighAccuracy)
                    .getOrNull()
            if (location != null) {
                val position =
                    Position(
                        latitude = location.coordinates.latitude,
                        longitude = location.coordinates.longitude
                    )
                onLocationReceived(position)
                _uiState.value =
                    _uiState.value.copy(gpsState = GpsState.FIXED, userPosition = position)
            } else {
                _uiState.value = _uiState.value.copy(gpsState = GpsState.UNAVAILABLE)
            }
        }
    }

    fun unfixGpsPosition() {
        _uiState.value = _uiState.value.copy(gpsState = GpsState.NOT_FIXED)
    }

    fun updateSearchQuery(query: String) {
        val searchResults =
            if (query.length < 2) {
                emptyList()
            } else {
                mapPointRepository.getMapPointsSearchResults(query)
            }
        _uiState.value = _uiState.value.copy(searchQuery = query, searchResults = searchResults)
    }

    fun toggleFilter(category: MapPointCategory) {
        val newFilters =
            if (_uiState.value.selectedFilters.contains(category)) {
                _uiState.value.selectedFilters - category
            } else {
                _uiState.value.selectedFilters + category
            }
        val mapFilterExpressions =
            newFilters.map {
                feature.get("category").asString().eq(const(it.name.lowercase()))
            }
        val newMapFilter =
            if (mapFilterExpressions.isNotEmpty()) {
                any(*mapFilterExpressions.toTypedArray())
            } else {
                nil()
            }
        _uiState.value = _uiState.value.copy(selectedFilters = newFilters, mapFilter = newMapFilter)
    }

    fun selectMapPoint(id: Int) {
        mapPointRepository.getMapPointById(id)?.let {
            _uiState.value = _uiState.value.copy(selectedMapPoint = it)
        }
    }

    fun selectSearchQuery(mapPoint: MapPoint) {
        _uiState.value =
            _uiState.value.copy(
                selectedMapPoint = mapPoint,
                isSearchExpanded = false
            )
    }

    fun clearSelectedMapPoint() {
        _uiState.value = _uiState.value.copy(selectedMapPoint = null)
    }
}