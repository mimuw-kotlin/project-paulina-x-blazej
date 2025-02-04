package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.source.NewsRemoteDataSource

class DefaultNewsRepository(
    source: NewsRemoteDataSource
) : NewsRepository {
    override val newsStream: Flow<List<News>>
        get() = TODO("Not yet implemented")

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }
}