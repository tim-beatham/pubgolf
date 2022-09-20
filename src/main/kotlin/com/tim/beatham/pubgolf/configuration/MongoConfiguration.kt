package com.tim.beatham.pubgolf.configuration
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class MongoConfiguration : AbstractMongoClientConfiguration() {
    companion object {
        const val DATABASE_NAME = "pubgolf"
    }

    override fun getDatabaseName(): String {
        return DATABASE_NAME
    }
}