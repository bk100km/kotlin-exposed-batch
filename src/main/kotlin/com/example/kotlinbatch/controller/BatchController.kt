package com.example.kotlinbatch.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/batch")
class BatchController(
    private val jobLauncher: JobLauncher,
    private val jobRegistry: JobRegistry
) {

    @PostMapping("/run")
    fun runBatch(@RequestParam jobName: String): ResponseEntity<String> {
        return try {
            val job: Job = jobRegistry.getJob(jobName)
            val jobParameters = JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters()
            jobLauncher.run(job, jobParameters)
            ResponseEntity.ok("Batch job '$jobName' started successfully!")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Batch job '$jobName' failed to start: ${e.message}")
        }
    }
}
