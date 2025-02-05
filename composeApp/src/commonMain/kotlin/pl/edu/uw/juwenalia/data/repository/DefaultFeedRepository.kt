package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import pl.edu.uw.juwenalia.data.file.downloadFeed
import pl.edu.uw.juwenalia.data.file.getArtists
import pl.edu.uw.juwenalia.data.file.getFiles
import pl.edu.uw.juwenalia.data.file.getNews
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.source.FeedRemoteDataSource

class DefaultFeedRepository(
    private val source: FeedRemoteDataSource
) : FeedRepository {

    private val _news: MutableStateFlow<List<News>> =
        MutableStateFlow(getNews())
    private val _artists: MutableStateFlow<List<Artist>> =
        MutableStateFlow(getArtists())

    override val newsStream = _news.asStateFlow()
    override val artistStream = _artists.asStateFlow()

    override suspend fun refresh() {
        source.fetchFeed()
        updateFlows()
    }

    private fun updateFlows() {
        _news.value = getNews()
        _artists.value = getArtists()
    }
}