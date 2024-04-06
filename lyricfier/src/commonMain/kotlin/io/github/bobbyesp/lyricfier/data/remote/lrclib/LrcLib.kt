package io.github.bobbyesp.lyricfier.data.remote.lrclib

import io.github.bobbyesp.lyricfier.data.remote.lrclib.LrcLibHttpRoutes.SEARCH
import io.github.bobbyesp.lyricfier.data.remote.lrclib.LrcLibHttpRoutes.buildGetSongById
import io.github.bobbyesp.lyricfier.domain.Provider
import io.github.bobbyesp.lyricfier.domain.dto.SyncedLines
import io.github.bobbyesp.lyricfier.domain.dto.lrclib.LrcLibResponse
import io.github.bobbyesp.lyricfier.domain.dto.lrclib.LrcLibResponse.Companion.toSongInfo
import io.github.bobbyesp.lyricfier.domain.model.SongInfo
import io.github.bobbyesp.lyricfier.domain.repository.LyricsProvider
import io.github.bobbyesp.lyricfier.utils.KermitLogger
import io.github.bobbyesp.lyricfier.utils.KtorUtils.client
import io.github.bobbyesp.lyricfier.utils.KtorUtils.json
import io.github.bobbyesp.lyricfier.utils.LyricsUtil
import io.github.bobbyesp.lyricfier.utils.LyricsUtil.deleteEmptyLines
import io.github.bobbyesp.lyricfier.utils.URLUtils.toEncodedUrl
import io.ktor.client.call.body
import io.ktor.client.request.get

open class LrcLib : LyricsProvider {
    override val provider: Provider
        get() = Provider.LRCLIB
    override val syncedSupport: Boolean
        get() = true
    override val plainSupport: Boolean
        get() = true
    override val baseUrl: String
        get() = LrcLibHttpRoutes.LYRICS_API_BASE

    /**
     * This function is used to search for songs using the LrcLib API.
     *
     * @param searchQuery The query string to search for. This should be the name of the song or artist.
     * @return A list of LrcLibResponse objects representing the search results. Each object contains information about a song.
     * @throws IllegalArgumentException If the search query is blank.
     * @throws Exception If there is an error while making the API request or processing the response.
     */
    open suspend fun search(searchQuery: String): List<LrcLibResponse>? {
        // Check if the search query is blank. If it is, throw an IllegalArgumentException.
        if (searchQuery.isBlank()) throw IllegalArgumentException("Search query must not be blank")

        // Encode the search query to be used in a URL.
        val search = searchQuery.toEncodedUrl()

        return try {
            // Make a GET request to the LrcLib API with the search query as a parameter.
            val apiResponse: String = client.get(SEARCH) {
                url {
                    parameters.append("q", search)
                }
            }.body()

            // Decode the JSON response into a list of LrcLibResponse objects.
            json.decodeFromString<List<LrcLibResponse>>(apiResponse)
        } catch (e: Exception) {
            // Log the error and return null if there is an exception.
            KermitLogger.e("Error while getting song info from LrcLib", e)
            null
        }
    }

    /**
     * This function is used to get the best match for a song from the search results.
     *
     * @param searchQuery The query string to search for. This should be the name of the song or artist.
     * @return A SongInfo object representing the best match from the search results. If no match is found, returns null.
     */
    open suspend fun getBestResultSongInfo(searchQuery: String): SongInfo? {
        return search(searchQuery)?.firstOrNull()?.toSongInfo()
    }

    /**
     * This function is used to get a song by its ID from the LrcLib API.
     *
     * @param id The ID of the song to retrieve. This should be a unique identifier for a song in the LrcLib database.
     * @return A LrcLibResponse object representing the song. This object contains information about the song, including its lyrics.
     * @throws Exception If there is an error while making the API request or processing the response.
     */
    private suspend fun getSongById(id: Int): LrcLibResponse {
        val apiResponse: String = client.get(buildGetSongById(id)).body()
        return json.decodeFromString<LrcLibResponse>(apiResponse)
    }

    /**
     * This function is used to get the plain lyrics for a song from the LrcLib API.
     *
     * @param songInfo The SongInfo object containing information about the song. This should include the song's ID, name, and artist.
     * @return A string representing the plain lyrics for the song.
     * @throws IllegalArgumentException If the SongInfo object does not contain a valid ID. The ID is used to download the lyrics from the LrcLib API.
     * @throws Exception If there is an error while making the API request, processing the response, or if no plain lyrics are found.
     */
    override suspend fun getPlainLyrics(songInfo: SongInfo): String {
        // use the id to get the lyrics, and if the id is null, use the name and artist, get the best result and use the id from that
        val lrcLibResponse = getSongById(
            songInfo.id?.toInt()
                ?: getBestResultSongInfo("${songInfo.name} ${songInfo.artist}")?.id?.toInt()
                ?: throw IllegalArgumentException("SongInfo id must not be null; it will be used for downloading the lyrics.")
        )

        return lrcLibResponse.plainLyrics ?: throw Exception("No plain lyrics found")
    }

    /**
     * This function is used to get the synced lyrics for a song from the LrcLib API.
     *
     * @param songInfo The SongInfo object containing information about the song. This should include the song's ID, name, and artist.
     * @return A SyncedLines object representing the synced lyrics for the song. This object contains a list of lines, each with a timestamp and the corresponding lyrics.
     *         If there is an error while getting the lyrics, returns a SyncedLines object with the error flag set to true and an empty list of lines.
     * @throws IllegalArgumentException If the SongInfo object does not contain a valid ID. The ID is used to download the lyrics from the LrcLib API.
     * @throws Exception If there is an error while making the API request or processing the response.
     */
    override suspend fun getSyncedLyrics(songInfo: SongInfo): SyncedLines {
        return try {
            val lrcLibResponse = getSongById(
                songInfo.id?.toInt()
                    ?: getBestResultSongInfo("${songInfo.name} ${songInfo.artist}")?.id?.toInt()
                    ?: throw IllegalArgumentException("SongInfo id must not be null; it will be used for downloading the lyrics.")
            )

            return SyncedLines(error = false,
                syncType = "LINE_SYNCED",
                lines = lrcLibResponse.syncedLyrics?.run { LyricsUtil.parseLyrics(this) }
                    ?: emptyList()).deleteEmptyLines()
        } catch (e: Exception) {
            KermitLogger.e("Error while getting synced lyrics from LrcLib", e)
            SyncedLines(
                error = true, syncType = null, lines = emptyList()
            )
        }
    }
}