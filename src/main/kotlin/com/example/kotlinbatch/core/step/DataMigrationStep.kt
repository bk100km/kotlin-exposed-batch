package com.example.kotlinbatch.core.step

import com.example.kotlinbatch.core.reader.MySQLReader
import com.example.kotlinbatch.core.writer.PostgreSQLWriter
import com.example.kotlinbatch.domain.Users
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class DataMigrationStep(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val mysqlReader: MySQLReader,
    private val postgresqlWriter: PostgreSQLWriter
) {

    @Bean
    fun createDataMigrationStep(): Step {
        return StepBuilder("dataMigrationStep", jobRepository)
            .chunk<Users, Users>(100, transactionManager)
            .reader(mysqlReader)
            .writer(postgresqlWriter)
            .build()
    }
}
