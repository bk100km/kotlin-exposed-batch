package com.example.kotlinbatch.core.writer

import com.example.kotlinbatch.domain.Users
import com.example.kotlinbatch.domain.UsersEntity
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import javax.sql.DataSource
import org.jetbrains.exposed.sql.Database

import org.springframework.batch.item.Chunk

@Component
class PostgreSQLWriter(private val postgresqlDataSource: DataSource) : ItemWriter<Users> {

    init {
        Database.connect(postgresqlDataSource)
    }

    override fun write(chunk: Chunk<out Users>) {
        transaction {
            chunk.forEach { user ->
                UsersEntity.insert {
                    it[name] = user.name
                    it[age] = user.age
                }
            }
        }
    }
}
