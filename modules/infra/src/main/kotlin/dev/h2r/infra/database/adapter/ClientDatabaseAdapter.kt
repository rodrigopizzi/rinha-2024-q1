package dev.h2r.infra.database.adapter

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import dev.h2r.domain.entity.Client
import dev.h2r.domain.entity.Transaction
import dev.h2r.domain.entity.TransactionType
import dev.h2r.domain.port.ClientDatabasePort
import dev.h2r.infra.database.DatabaseSingleton
import org.bson.Document
import java.time.LocalDateTime
import java.time.ZoneOffset

class ClientDatabaseAdapter : ClientDatabasePort {

    private val clientCollection: MongoCollection<Document> by lazy {
        DatabaseSingleton.database.getCollection("clients")
    }

    override fun updateBalance(client: Client, transaction: Transaction, newBalance: Int): Client? {
        val raceCondition = Filters.and(
            Filters.eq("_id", client.id),
            Filters.eq("balance", client.balance)
        )

        val update = Updates.combine(
            Updates.set("balance", newBalance),
            Updates.push(
                "transactions", Document(
                    "\$each", listOf(
                        Document(
                            mapOf(
                                "value" to transaction.value,
                                "type" to transaction.type,
                                "description" to transaction.description,
                                "date" to LocalDateTime.now()
                            )
                        )
                    )
                ).append("\$slice", -10)
            )
        )

        val options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)

        return clientCollection.findOneAndUpdate(raceCondition, update, options)?.let {
            convertDocumentToClient(it)
        }
    }

    override fun find(idClient: Int): Client? {
        val filter = Filters.and(
            Filters.eq("_id", idClient)
        )
        return clientCollection.find(filter).first()?.let {
            convertDocumentToClient(it)
        }
    }

    private fun convertDocumentToClient(doc: Document) = Client(
        id = doc.getInteger("_id"),
        balance = doc.getInteger("balance"),
        limit = doc.getInteger("limit"),
        last10Transactions = doc.getList("transactions", Document::class.java)
            .map {
                Transaction(
                    value = it.getInteger("value"),
                    type = TransactionType.valueOf(it.getString("type")),
                    date = it.getDate("date").toInstant().atZone(ZoneOffset.UTC).toLocalDateTime(),
                    description = it.getString("description")
                )
            }.sortedByDescending { it.date }
    )

}