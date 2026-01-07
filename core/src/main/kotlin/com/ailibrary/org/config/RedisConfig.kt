package com.ailibrary.org.config

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection

object RedisFactory {
    private val redisClient: RedisClient by lazy {
        RedisClient.create(
            RedisURI.builder()
                .withHost(System.getenv("REDIS_HOST") ?: "localhost")
                .withPort(System.getenv("REDIS_PORT")?.toInt() ?: 6379)
                .build()
        )
    }

    private val connection: StatefulRedisConnection<String, String> by lazy {
        redisClient.connect()
    }

    private val commands = connection.async()

    // step 1
    // run first test commadn and check it's working
    // Resiliance4j will be added later


    //step 2
    // up the container postgres
    // make the migration file for the user table
    // connect exposed to postgres


    suspend fun TestCommand() {
        commands.set("foo", "bar")
        val value = commands.get("foo")
        println("Value for 'foo': $value")
    }
//    suspend fun setValueWithTTL(key: String, value: String, ttlSeconds: Long) {
//        executeResilient {
//            commands.setex(key, ttlSeconds, value).await()
//        }
//    }
}