package io.github.bobbyesp.lyricfier.domain

enum class Provider {
    SPOTIFY,
    LRCLIB,
    NETEASE;

    open override fun toString(): String {
        return when (this) {
            SPOTIFY -> "Spotify"
            LRCLIB -> "LrcLib"
            NETEASE -> "NetEase"
        }
    }

    open fun String.toProvider(): Provider {
        return when (this) {
            "Spotify" -> SPOTIFY
            "LrcLib" -> LRCLIB
            "NetEase" -> NETEASE
            else -> throw IllegalArgumentException("Unknown provider: $this")
        }
    }
}