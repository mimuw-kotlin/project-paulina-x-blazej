package pl.edu.uw.juwenalia.data.source

import pl.edu.uw.juwenalia.data.file.ARTIST_IMAGES_FOLDER
import pl.edu.uw.juwenalia.data.file.NEWS_IMAGES_FOLDER
import pl.edu.uw.juwenalia.data.file.SPONSOR_IMAGES_FOLDER
import pl.edu.uw.juwenalia.data.file.downloadFeed
import pl.edu.uw.juwenalia.data.file.getFeedData
import pl.edu.uw.juwenalia.data.file.getFileBytesByName
import pl.edu.uw.juwenalia.data.model.Artist
import pl.edu.uw.juwenalia.data.model.News
import pl.edu.uw.juwenalia.data.model.Sponsor

class DefaultFeedRemoteDataSource() : FeedRemoteDataSource {
    override suspend fun fetchFeed() {
        downloadFeed()
    }

    override fun getNews(): List<News> {
        val newsJsonData = getFeedData().newsData
        val newsList = emptyList<News>().toMutableList()
        newsJsonData.forEach { news ->
            val byteArray = getFileBytesByName(
                NEWS_IMAGES_FOLDER,
                news.imageFilename)
            if (byteArray != null) {
                newsList += News(
                    id = news.id,
                    title = news.title,
                    imageFilename = news.imageFilename,
                    content = news.content,
                    imageByteArray = byteArray
                )
            }
        }
        return newsList.sortedByDescending { it.id }
    }

    override fun getArtists(): List<Artist> {
        val artistsJsonData = getFeedData().artistData
        val artistsList = emptyList<Artist>().toMutableList()
        artistsJsonData.forEach { artist ->
            val byteArray = getFileBytesByName(
                ARTIST_IMAGES_FOLDER,
                artist.imageFilename)
            if (byteArray != null) {
                artistsList += Artist(
                    id = artist.id,
                    name = artist.name,
                    imageFilename = artist.imageFilename,
                    description = artist.description,
                    imageByteArray = byteArray
                )
            }
        }
        return artistsList.sortedByDescending { it.id }
    }

    override fun getSponsors(): List<Sponsor> {
        val sponsorsJsonData = getFeedData().sponsorsData
        val sponsorsList = emptyList<Sponsor>().toMutableList()
        sponsorsJsonData.forEach { sponsor ->
            val byteArray = getFileBytesByName(
                SPONSOR_IMAGES_FOLDER,
                sponsor.imageFilename)
            if (byteArray != null) {
                sponsorsList += Sponsor(
                    id = sponsor.id,
                    name = sponsor.name,
                    imageFilename = sponsor.imageFilename,
                    url = sponsor.url,
                    imageByteArray = byteArray
                )
            }
        }
        return sponsorsList.sortedByDescending { it.id }
    }

}