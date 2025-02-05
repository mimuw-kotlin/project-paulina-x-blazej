package pl.edu.uw.juwenalia.data.source

interface FeedRemoteDataSource {
    suspend fun fetchFeed()
}