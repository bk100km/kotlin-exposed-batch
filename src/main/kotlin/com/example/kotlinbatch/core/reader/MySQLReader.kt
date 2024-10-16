package com.example.kotlinbatch.core.reader

import com.example.kotlinbatch.domain.Users
import com.example.kotlinbatch.domain.UsersEntity
import org.jetbrains.exposed.sql.selectAll
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import javax.sql.DataSource
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow

@Component
class MySQLReader(mysqlDataSource: DataSource) : ItemReader<Users> {

    init {
        Database.connect(mysqlDataSource)
    }

    private var currentData: MutableList<Users>? = null

    private fun toUsers(row: ResultRow): Users {
        return Users(
            id = row[UsersEntity.id].value,
            name = row[UsersEntity.name],
            age = row[UsersEntity.age]
        )
    }

    override fun read(): Users? {
        if (currentData == null) {
            currentData = transaction {
                UsersEntity.selectAll().map { toUsers(it) }.toMutableList()
            }
        }
        return currentData?.takeIf { it.isNotEmpty() }?.removeAt(0)
    }
}
