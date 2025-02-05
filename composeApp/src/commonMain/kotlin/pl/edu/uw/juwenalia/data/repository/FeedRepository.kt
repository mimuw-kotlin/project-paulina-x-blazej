package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor

interface FeedRepository {
    val newsStream: Flow<List<News>>
    val artistStream: Flow<List<Artist>>
    val sponsorStream: Flow<List<Sponsor>>

    suspend fun refresh()
}