package pl.edu.uw.juwenalia.data.source

import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor

interface FeedRemoteDataSource {
    suspend fun fetchFeed()

    fun getNews(): List<News>
    fun getArtists(): List<Artist>
    fun getSponsors(): List<Sponsor>
}