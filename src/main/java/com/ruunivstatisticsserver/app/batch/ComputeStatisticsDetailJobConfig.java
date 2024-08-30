package com.ruunivstatisticsserver.app.batch;

import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ComputeStatisticsDetailJobConfig {
    private final ComputeStatisticsDetailReader computeStatisticsDetailReader;
    private final ComputeStatisticsDetailWriter computeStatisticsDetailWriter;

    @Bean
    public Job withDrawUserJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("computeStatisticsDetailJob", jobRepository)
                .start(withDrawUserStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step withDrawUserStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("computeStatisticsDetailStep", jobRepository)
                .<StatisticsDetail, StatisticsDetail>chunk(500, transactionManager)
                .reader(computeStatisticsDetailReader)
                .writer(computeStatisticsDetailWriter)
                .build();
    }
}
