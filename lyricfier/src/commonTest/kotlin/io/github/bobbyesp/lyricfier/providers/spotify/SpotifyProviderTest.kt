package io.github.bobbyesp.lyricfier.providers.spotify

import io.github.bobbyesp.lyricfier.data.remote.spotify.Spotify
import io.github.bobbyesp.lyricfier.domain.model.SongInfo
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.asserter

class SpotifyProviderTest {
    @Test
    fun getLyricsOf_REINA_by_Mora() = runTest {
        val spotifyProvider = Spotify()
        val songInfo = SongInfo(
            name = "REINA",
            artist = "Mora, Saiko",
            link = "https://open.spotify.com/track/0QRHOAeU8JRaVVn3UoZBih?si=4a2664b653a64e4f"
        )
        val lyrics = spotifyProvider.getSyncedLyrics(songInfo)
        println(lyrics)
    }
}