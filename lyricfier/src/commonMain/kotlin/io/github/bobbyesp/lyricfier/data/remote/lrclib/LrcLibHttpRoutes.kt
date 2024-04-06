package io.github.bobbyesp.lyricfier.data.remote.lrclib

internal object LrcLibHttpRoutes {
    const val LYRICS_API_BASE = "https://lrclib.net/api"

    const val SEARCH = "${LYRICS_API_BASE}/search"

    const val GET_LYRICS = "${LYRICS_API_BASE}/get"
    fun buildGetSongById(id: Int) = "$GET_LYRICS/$id"
}