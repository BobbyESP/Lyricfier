package com.bobbyesp.lyricfier.domain.dto.lrclib

import kotlinx.serialization.Serializable

@Serializable
data class LrcLibResponse(
    val id: Int,
    val name: String,
    val trackName: String,
    val artistName: String,
    val albumName: String,
    val duration: Int,
    val instrumental: Boolean,
    val plainLyrics: String?,
    val syncedLyrics: String?
) {
    companion object {
        fun empty() = LrcLibResponse(0, "", "", "", "", 0, false, null, null)
        fun LrcLibResponse.toSongInfo() = com.bobbyesp.lyricfier.domain.model.SongInfo(
            name = this.trackName,
            artist = this.artistName,
            album = this.albumName,
            duration = this.duration,
            id = this.id.toString()
        )
    }
}
