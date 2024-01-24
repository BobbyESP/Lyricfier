package com.bobbyesp.lyricfier.domain

/**
 * Enum class that represents the format of the lyrics.
 * This is used to know how to parse the lyrics.
 * ID3 is the plain format.
 * LRC is the synced format.
 */
enum class LyricsFormat {
    ID3, LRC;

    override fun toString(): String {
        return when (this) {
            ID3 -> "id3"
            LRC -> "lrc"
        }
    }

    fun String.toSpotifyLyricsFormat(): LyricsFormat {
        return when (this) {
            "id3" -> ID3
            "lrc" -> LRC
            else -> throw IllegalArgumentException("Unknown SpotifyLyricsFormat: $this")
        }
    }
}