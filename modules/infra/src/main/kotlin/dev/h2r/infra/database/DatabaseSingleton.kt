package dev.h2r.infra.database

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import java.util.concurrent.TimeUnit

object DatabaseSingleton {

    private lateinit var mongoClient: MongoClient
    lateinit var database: MongoDatabase

    fun closeConnection() {
        mongoClient.close()
    }

    fun startConnection(
        host: String, port: String, databaseName: String, user: String, password: String
    ) {
        val credential = MongoCredential.createScramSha1Credential(
            user, databaseName, password.toCharArray()
        )

        val settings = MongoClientSettings.builder()
            .credential(credential)
            .applyToClusterSettings { builder ->
                builder.hosts(listOf(ServerAddress(host, port.toInt())))
            }.applyToConnectionPoolSettings {
                it.maxSize(50)
                it.maxWaitTime(1000, TimeUnit.MILLISECONDS)
            }.build()


        mongoClient = MongoClients.create(settings)
        database = mongoClient.getDatabase("rinha")
    }

}