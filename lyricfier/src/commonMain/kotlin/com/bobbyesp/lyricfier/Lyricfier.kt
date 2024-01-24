package com.bobbyesp.lyricfier

import co.touchlab.kermit.Logger
import com.bobbyesp.lyricfier.data.remote.spotify.Spotify
import org.koin.core.context.startKoin

open class Lyricfier {
    fun initialize() {
        Logger.i("Lyricfier initialization started")

        startKoin {
            modules()
        }
    }
}