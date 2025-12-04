package com.dhanush.di

import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    startKoin {
        slf4jLogger()
        modules(koinModule)
    }
}