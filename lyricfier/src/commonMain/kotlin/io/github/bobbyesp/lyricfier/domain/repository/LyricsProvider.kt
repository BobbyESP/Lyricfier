package io.github.bobbyesp.lyricfier.domain.repository

import io.github.bobbyesp.lyricfier.domain.Provider
import io.github.bobbyesp.lyricfier.domain.dto.SyncedLines
import io.github.bobbyesp.lyricfier.domain.model.SongInfo

interface LyricsProvider {
    /**
     * The name of the [LyricsProvider]
     */
    val provider: Provider

    /**
     * Is the [LyricsProvider] able to provide synced lyrics?
     */
    val syncedSupport: Boolean

    /**
     * Is the [LyricsProvider] able to provide plain lyrics?
     */
    val plainSupport: Boolean

    /**
     * The base url of the [LyricsProvider] API
     */
    val baseUrl: String

    /**
     * Returns the plain lyrics for the given [SongInfo]
     * @param songInfo The [SongInfo] to get the lyrics for
     * @return The plain lyrics
     */
    suspend fun getPlainLyrics(songInfo: SongInfo): String

    /**
     * Returns the synced lyrics for the given [SongInfo]
     * @param songInfo The [SongInfo] to get the lyrics for
     * @return The synced lyrics in the form of a [SyncedLines] object
     */
    suspend fun getSyncedLyrics(songInfo: SongInfo): SyncedLines
}