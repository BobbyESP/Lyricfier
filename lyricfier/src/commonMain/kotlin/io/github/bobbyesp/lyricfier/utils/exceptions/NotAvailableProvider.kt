package io.github.bobbyesp.lyricfier.utils.exceptions

import io.github.bobbyesp.lyricfier.domain.LyricsFormat

/**
 * Exception thrown when a provider is not available.
 * This should be used when the lyrics provider cannot return some time of lyrics (plain or synced).
 */
class NotAvailableProvider(message: String) :
    Exception(message) {
    constructor() : this("The provider is not available")

    constructor(lyricsNotAbleToProvide: LyricsFormat) : this(
        "The provider is not able to provide ${
            lyricsNotAbleToProvide.toString().lowercase()
        } lyrics"
    )
}