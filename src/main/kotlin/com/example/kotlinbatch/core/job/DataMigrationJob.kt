package com.example.kotlinbatch.core.job

import com.example.kotlinbatch.core.step.DataMigrationStep
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataMigrationJob(
    private val jobRepository: JobRepository,
    private val dataMigrationStep: DataMigrationStep
) {

    @Bean
    fun createDataMigrationJob(): Job {
        return JobBuilder("dataMigrationJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(dataMigrationStep.createDataMigrationStep())
            .build()
    }
}
