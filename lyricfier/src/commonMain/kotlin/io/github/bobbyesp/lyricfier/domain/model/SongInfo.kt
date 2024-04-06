package io.github.bobbyesp.lyricfier.domain.model

data class SongInfo(
    val name: String,
    val artist: String,
    val album: String? = null,
    val link: String? = null,
    val coverLink : String? = null,
    val duration: Int? = null,
    val id: String? = null,
) {
   companion object {
       fun empty() = SongInfo("", "")
   }
}
