package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.uw.juwenalia.data.model.News

interface NewsRepository {
    val newsStream: Flow<List<News>>

    suspend fun refresh()
}