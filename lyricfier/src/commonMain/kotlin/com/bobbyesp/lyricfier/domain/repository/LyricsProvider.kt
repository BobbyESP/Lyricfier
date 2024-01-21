package com.bobbyesp.lyricfier.domain.repository

import com.bobbyesp.lyricfier.domain.Provider
import com.bobbyesp.lyricfier.domain.dto.SyncedLines
import com.bobbyesp.lyricfier.domain.model.SongInfo

abstract class LyricsProvider {
    /**
     * The name of the [LyricsProvider]
     */
    abstract val provider: Provider

    /**
     * Is the [LyricsProvider] able to provide synced lyrics?
     */
    abstract val syncedSupport: Boolean

    /**
     * Is the [LyricsProvider] able to provide plain lyrics?
     */
    abstract val plainSupport: Boolean

    /**
     * The base url of the [LyricsProvider] API
     */
    abstract val baseUrl: String

    /**
     * Returns the plain lyrics for the given [SongInfo]
     * @param songInfo The [SongInfo] to get the lyrics for
     * @return The plain lyrics
     */
    abstract suspend fun getPlainLyrics(songInfo: SongInfo): String

    /**
     * Returns the synced lyrics for the given [SongInfo]
     * @param songInfo The [SongInfo] to get the lyrics for
     * @return The synced lyrics in the form of a [SyncedLines] object
     */
    abstract suspend fun getSyncedLyrics(songInfo: SongInfo): SyncedLines
}