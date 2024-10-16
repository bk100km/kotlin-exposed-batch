package com.example.kotlinbatch.config

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfig {

    @Bean
    fun mysqlDataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/mydb")
            .username("mysql_user")
            .password("mysql_password")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build()
    }

    @Bean
    fun postgresqlDataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/mydb")
            .username("postgres_user")
            .password("postgres_password")
            .driverClassName("org.postgresql.Driver")
            .build()
    }
}
