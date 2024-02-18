package dev.h2r.domain.port

import dev.h2r.domain.entity.Client
import dev.h2r.domain.entity.Transaction
import java.math.BigInteger

interface ClientDatabasePort {
    fun updateBalance(client: Client, transaction: Transaction, newBalance: BigInteger): Client?
    fun find(idClient: Int): Client?

}