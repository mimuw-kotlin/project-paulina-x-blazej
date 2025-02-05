package pl.edu.uw.juwenalia.data.source

import pl.edu.uw.juwenalia.data.file.downloadFeed

class DefaultFeedRemoteDataSource() : FeedRemoteDataSource {
    override suspend fun fetchFeed() {
        downloadFeed()
    }

}