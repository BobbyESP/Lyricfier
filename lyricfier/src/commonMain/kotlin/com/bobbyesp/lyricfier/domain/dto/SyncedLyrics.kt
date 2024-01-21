package com.bobbyesp.lyricfier.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class SyncedLines(
    val error: Boolean = false,
    val syncType: String? = null,
    val lines: List<Line>
)

@Serializable
data class Line(
    val timeTag: String,
    val words: String
)