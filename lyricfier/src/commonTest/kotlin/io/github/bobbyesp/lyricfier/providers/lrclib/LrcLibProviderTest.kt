package io.github.bobbyesp.lyricfier.providers.lrclib

import io.github.bobbyesp.lyricfier.data.remote.lrclib.LrcLib
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LrcLibProviderTest {
    @Test
    fun getLyricsOf_Yesterday_By_AlanWalker_Synced() = runTest {
        val lrcLibProvider = LrcLib()
        val songById = lrcLibProvider.getBestResultSongInfo("Yesterday Alan Walker") ?: throw Exception("Song not found")
        val syncedLyrics = lrcLibProvider.getSyncedLyrics(songById)
        println(syncedLyrics)
    }

    @Test
    fun getLyricsOf_Yesterday_By_AlanWalker_Plain() = runTest {
        val lrcLibProvider = LrcLib()
        val songById = lrcLibProvider.getBestResultSongInfo("Yesterday Alan Walker") ?: throw Exception("Song not found")
        val syncedLyrics = lrcLibProvider.getPlainLyrics(songById)
        println(syncedLyrics)
    }
}