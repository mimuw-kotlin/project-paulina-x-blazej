package pl.edu.uw.juwenalia.data.model

import io.github.dellisd.spatialk.geojson.Position

data class MapPoint(
    val id: Int,
    val name: String,
    val position: Position,
    val category: MapPointCategory,
    val additionalInfo: String? = null
)