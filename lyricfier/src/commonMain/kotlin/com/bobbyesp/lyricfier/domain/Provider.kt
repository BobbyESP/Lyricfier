package com.bobbyesp.lyricfier.domain

enum class Provider {
    SPOTIFY,
    LRCLIB,
    NETEASE;

    override fun toString(): String {
        return when (this) {
            SPOTIFY -> "Spotify"
            LRCLIB -> "LrcLib"
            NETEASE -> "NetEase"
        }
    }

    fun String.toProvider(): Provider {
        return when (this) {
            "Spotify" -> SPOTIFY
            "LrcLib" -> LRCLIB
            "NetEase" -> NETEASE
            else -> throw IllegalArgumentException("Unknown provider: $this")
        }
    }

//    fun getProvider(): LyricsProvider {
//        return when (this) {
//            SPOTIFY -> SpotifyLyricsProvider()
//            LRCLIB -> LrcLibLyricsProvider()
//            NETEASE -> NetEaseLyricsProvider()
//        }
//    }
}