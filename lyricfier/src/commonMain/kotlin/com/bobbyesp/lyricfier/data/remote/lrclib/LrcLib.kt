package com.bobbyesp.lyricfier.data.remote.lrclib

import com.bobbyesp.lyricfier.domain.Provider
import com.bobbyesp.lyricfier.domain.dto.SyncedLines
import com.bobbyesp.lyricfier.domain.dto.lrclib.LrcLibResponse
import com.bobbyesp.lyricfier.domain.dto.lrclib.LrcLibResponse.Companion.toSongInfo
import com.bobbyesp.lyricfier.domain.model.SongInfo
import com.bobbyesp.lyricfier.domain.repository.LyricsProvider
import com.bobbyesp.lyricfier.utils.KermitLogger
import com.bobbyesp.lyricfier.utils.KtorUtils.client
import com.bobbyesp.lyricfier.utils.KtorUtils.json
import com.bobbyesp.lyricfier.utils.LyricsUtil
import com.bobbyesp.lyricfier.utils.LyricsUtil.deleteEmptyLines
import com.bobbyesp.lyricfier.utils.URLUtils.toEncodedUrl
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

    open suspend fun search(searchQuery: String): List<LrcLibResponse>? {
        if (searchQuery.isBlank()) throw IllegalArgumentException("Search query must not be blank")
        val search = searchQuery.toEncodedUrl()

        return try {
            val apiResponse: String = client.get("$baseUrl/search") {
                url {
                    parameters.append("q", search)
                }
            }.body()
            json.decodeFromString<List<LrcLibResponse>>(apiResponse)
        } catch (e: Exception) {
            KermitLogger.e("Error while getting song info from LrcLib", e)
            null
        }
    }

    open suspend fun getBestResultSongInfo(searchQuery: String): SongInfo? {
        return search(searchQuery)?.firstOrNull()?.toSongInfo()
    }

    private suspend fun getLyricsById(id: Int): LrcLibResponse {
        val apiResponse: String = client.get("$baseUrl/get/$id").body()
        return json.decodeFromString<LrcLibResponse>(apiResponse)
    }

    override suspend fun getPlainLyrics(songInfo: SongInfo): String {
        val lrcLibResponse = getLyricsById(
            songInfo.id?.toInt()
                ?: throw IllegalArgumentException("SongInfo id must not be null; it will be used for downloading the lyrics.")
        )

        return lrcLibResponse.plainLyrics ?: ""
    }

    override suspend fun getSyncedLyrics(songInfo: SongInfo): SyncedLines {
        return try {
            val lrcLibResponse = getLyricsById(
                songInfo.id?.toInt()
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