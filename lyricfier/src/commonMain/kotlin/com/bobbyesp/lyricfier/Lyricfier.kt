package com.bobbyesp.lyricfier

import co.touchlab.kermit.Logger
import org.koin.core.context.startKoin

open class Lyricfier {
    fun initialize() {
        Logger.i("Lyricfier initialization started")

        startKoin {
            modules()
        }
    }
}