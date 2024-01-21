package com.bobbyesp.lyricfier.domain

enum class SpotifyLyricsFormat {
    ID3, LRC;

    override fun toString(): String {
        return when (this) {
            ID3 -> "id3"
            LRC -> "lrc"
        }
    }

    fun String.toSpotifyLyricsFormat(): SpotifyLyricsFormat {
        return when (this) {
            "id3" -> ID3
            "lrc" -> LRC
            else -> throw IllegalArgumentException("Unknown SpotifyLyricsFormat: $this")
        }
    }
}