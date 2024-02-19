package dev.h2r.domain.port

import dev.h2r.domain.entity.Client
import dev.h2r.domain.entity.Transaction

interface ClientDatabasePort {
    fun updateBalance(client: Client, transaction: Transaction, newBalance: Int): Client?
    fun find(idClient: Int): Client?

}