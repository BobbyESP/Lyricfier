package com.bobbyesp.lyricfier.utils

import com.bobbyesp.lyricfier.domain.dto.Line
import com.bobbyesp.lyricfier.domain.dto.SyncedLines

object LyricsUtil {
    fun parseLyrics(lyrics: String): List<Line> {
        val lines = lyrics.split("\n")
        val lyricsLines = mutableListOf<Line>()
        for (line in lines) {
            val timeTag = line.substring(1, 9)
            val words = line.substring(10)
            lyricsLines.add(Line(timeTag, words))
        }
        return lyricsLines
    }

    fun SyncedLines.toLyricsString(): String {
        val lines = this.lines
        val syncedLyricsResponse = StringBuilder()

        for (i in lines.indices) {
            val line = lines[i]
            if (line.words.isBlank()) continue
            with(syncedLyricsResponse) {
                append("[${line.timeTag}] ${line.words}")
                //if the next line is not blank, append a new line, else do nothing
                if (i < lines.size - 1 && lines[i + 1].words.isNotBlank()) append("\n")
            }
        }

        return syncedLyricsResponse.toString()
    }

    fun SyncedLines.deleteEmptyLines(): SyncedLines {
        val lines = this.lines.filter { it.words.isNotBlank() }
        return SyncedLines(this.error, this.syncType, lines)
    }

    // Function to parse the time in milliseconds from a .lrc line
    fun parseTimeFromLine(line: String): Long {
        //Parse the time from line, for example: "[00:12.34]"
        val timeString = line.substring(1, line.indexOf(']'))
        val (minutes, seconds, milliseconds) = timeString.split(":").map { it.toInt() }
        return (minutes * 60 + seconds) * 1000L + milliseconds
    }
}