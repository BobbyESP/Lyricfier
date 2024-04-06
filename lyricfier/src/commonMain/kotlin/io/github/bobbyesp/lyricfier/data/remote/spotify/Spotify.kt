package io.github.bobbyesp.lyricfier.data.remote.spotify

import io.github.bobbyesp.lyricfier.domain.Provider
import io.github.bobbyesp.lyricfier.domain.dto.SyncedLines
import io.github.bobbyesp.lyricfier.domain.model.SongInfo
import io.github.bobbyesp.lyricfier.domain.repository.LyricsProvider
import io.github.bobbyesp.lyricfier.utils.KtorUtils.client
import io.github.bobbyesp.lyricfier.utils.KtorUtils.json
import io.github.bobbyesp.lyricfier.utils.KtorUtils.manageError
import io.github.bobbyesp.lyricfier.utils.LyricsUtil.deleteEmptyLines
import io.github.bobbyesp.lyricfier.utils.exceptions.NotAvailableProvider
import io.ktor.client.call.body
import io.ktor.client.request.get

open class Spotify: LyricsProvider {
    override val provider: Provider
        get() = Provider.SPOTIFY
    override val syncedSupport: Boolean
        get() = true
    override val plainSupport: Boolean
        get() = false
    override val baseUrl: String
        get() = SpotifyHttpRoutes.LYRICS_API_BASE

    override suspend fun getPlainLyrics(songInfo: SongInfo): String {
        throw NotAvailableProvider("Spotify does not support plain lyrics (or at least by now)")
    }

    /**
     * Returns the synced lyrics for the given [SongInfo]
     * @param songInfo The [SongInfo] to get the lyrics for
     * @return The synced lyrics in the form of a [SyncedLines] object
     * @throws IllegalArgumentException if [SongInfo.link] is null. Only link is supported for now
     */
    override suspend fun getSyncedLyrics(songInfo: SongInfo): SyncedLines {
        return try {
            val apiResponse: String = client.get(baseUrl) {
                url {
                    parameters.append("url", songInfo.link ?: throw IllegalArgumentException("SongInfo link must not be null; it will be used for downloading the lyrics."))
                    parameters.append("format", "lrc")
                }
            }.body()
            json.decodeFromString<SyncedLines>(apiResponse).deleteEmptyLines()
        } catch (e: Exception) {
            manageError(e)
            SyncedLines(
                error = true,
                syncType = null,
                lines = emptyList()
            )
        }
    }
}