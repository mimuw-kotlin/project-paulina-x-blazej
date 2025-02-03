package pl.edu.uw.juwenalia.data.repository

import pl.edu.uw.juwenalia.data.model.MapPoint
import pl.edu.uw.juwenalia.data.source.StaticMapData

class DefaultMapPointRepository : MapPointRepository {
    private val mapPoints = StaticMapData.mapPoints

    override fun getMapPointById(id: Int): MapPoint? = mapPoints.find { it.id == id }

    override fun getMapPointsSearchResults(
        query: String,
        limit: Int
    ): List<MapPoint> {
        val titleMatches = mapPoints.filter { it.name.contains(query, ignoreCase = true) }
        val additionalInfoMatches =
            mapPoints.filter {
                it.additionalInfo?.contains(query, ignoreCase = true) == true &&
                    !titleMatches.contains(it)
            }
        return (titleMatches + additionalInfoMatches).take(limit)
    }
}