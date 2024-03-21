package com.bobbyesp.lyricfier.providers.lrclib

import com.bobbyesp.lyricfier.data.remote.lrclib.LrcLib
import com.bobbyesp.lyricfier.domain.model.SongInfo
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LrcLibProviderTest {
    @Test
    fun getLyricsOf_Yesterday_By_AlanWalker() = runTest {
        val lrcLibProvider = LrcLib()
        val songById = lrcLibProvider.getBestResultSongInfo("Yesterday Alan Walker") ?: throw Exception("Song not found")
        val syncedLyrics = lrcLibProvider.getSyncedLyrics(songById)
        println(syncedLyrics)
    }
}