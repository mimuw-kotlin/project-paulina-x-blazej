package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor

interface FeedRepository {
    val news: Flow<List<News>>
    val artists: Flow<List<Artist>>
    val sponsors: Flow<List<Sponsor>>

    suspend fun refresh()
}