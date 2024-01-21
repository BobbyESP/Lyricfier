package com.bobbyesp.lyricfier.utils

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
object KtorUtils {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val client = HttpClient {
        install(Logging) { // Install default logger for Ktor
            level = LogLevel.ALL
        }
        install(ContentNegotiation) { // Install JSON serializer using Kotlinx serialization
            json(
                contentType = ContentType.Application.Json,
                json = json
            )
        }
        install(HttpCache) // Install default cache (in-memory)
    }

    fun manageError(e: Exception) {
        when (e) {
            is RedirectResponseException -> {
                // 3XX - Responses
                KermitLogger.e("Error: ${e.response.status.description}")
            }

            is ClientRequestException -> {
                // 4XX - Responses
                KermitLogger.e("Error: ${e.response.status.description}")
            }

            is ServerResponseException -> {
                // 5XX - Responses
                KermitLogger.e("Error: ${e.response.status.description}")
            }

            else -> {
                KermitLogger.e("Error: ${e.message}")
            }
        }
    }
}

typealias KermitLogger = co.touchlab.kermit.Logger