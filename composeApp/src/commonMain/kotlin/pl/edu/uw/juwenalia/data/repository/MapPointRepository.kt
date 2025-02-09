package pl.edu.uw.juwenalia.data.repository

import pl.edu.uw.juwenalia.data.model.MapPoint

interface MapPointRepository {
    fun getMapPointById(id: Int): MapPoint?

    fun getMapPointsSearchResults(
        query: String,
        limit: Int = 10
    ): List<MapPoint>
}