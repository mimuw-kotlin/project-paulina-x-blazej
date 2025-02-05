package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News

interface FeedRepository {
    val newsStream: Flow<List<News>>
    val artistStream: Flow<List<Artist>>

    suspend fun refresh()
}