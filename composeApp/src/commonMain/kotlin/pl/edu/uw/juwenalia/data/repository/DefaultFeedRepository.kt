package pl.edu.uw.juwenalia.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor
import pl.edu.uw.juwenalia.data.source.FeedRemoteDataSource

class DefaultFeedRepository(
    private val source: FeedRemoteDataSource
) : FeedRepository {

    private val _news: MutableStateFlow<List<News>> =
        MutableStateFlow(source.getNews())
    private val _artists: MutableStateFlow<List<Artist>> =
        MutableStateFlow(source.getArtists())
    private val _sponsors: MutableStateFlow<List<Sponsor>> =
        MutableStateFlow(source.getSponsors())

    override val newsStream = _news.asStateFlow()
    override val artistStream = _artists.asStateFlow()
    override val sponsorStream = _sponsors.asStateFlow()

    override suspend fun refresh() {
        source.fetchFeed()
        updateFlows()
    }

    private fun updateFlows() {
        _news.value = source.getNews()
        _artists.value = source.getArtists()
        _sponsors.value = source.getSponsors()
    }
}