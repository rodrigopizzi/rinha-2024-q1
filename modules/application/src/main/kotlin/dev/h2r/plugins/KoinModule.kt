package dev.h2r.plugins

import dev.h2r.domain.port.ClientDatabasePort
import dev.h2r.domain.usecase.PerformTransactionUseCase
import dev.h2r.infra.database.adapter.ClientDatabaseAdapter
import dev.h2r.controller.ClientController
import dev.h2r.domain.usecase.StatementUseCase
import io.ktor.server.application.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.dependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(adapterModule)
        modules(useCaseModule)
        modules(controllerModule)
    }
}

private val adapterModule = module {
    singleOf(::ClientDatabaseAdapter) { bind<ClientDatabasePort>() }
}

private val useCaseModule = module {
    singleOf(::PerformTransactionUseCase)
    singleOf(::StatementUseCase)
}

private val controllerModule = module {
    singleOf(::ClientController)
}