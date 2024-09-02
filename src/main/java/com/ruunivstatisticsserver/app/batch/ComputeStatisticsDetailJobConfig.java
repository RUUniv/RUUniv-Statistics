package com.ruunivstatisticsserver.app.batch;

import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoCursorItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.MongoItemWriter.Mode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ComputeStatisticsDetailJobConfig {
    private final static String EXECUTION_PREFIX = "API_KEY: ";
    private final MongoTemplate mongoTemplate;
    @Setter
    private StepExecution stepExecution;

    @Bean(name = "computeStatisticsDetailJob")
    public Job computeStatisticsDetailJob(JobRepository jobRepository,
                                          @Qualifier("computeStep") Step computeStatisticsDetailStep)
            throws Exception {
        return new JobBuilder("computeStatisticsDetailJob", jobRepository)
                .start(computeStatisticsDetailStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean(name = "computeStep")
    public Step computeStatisticsDetailStep(JobRepository jobRepository,
                                            PlatformTransactionManager transactionManager)
            throws Exception {
        return new StepBuilder("computeStatisticsDetailStep", jobRepository)
                .<Statistics, Statistics>chunk(100, transactionManager)
                .reader(computeStatisticsDetailReader())
                .processor(computeStatisticsDetailProcessor())
                .writer(computeStatisticsDetailWriter())
                .listener(stepExecutionListener())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public MongoCursorItemReader<Statistics> computeStatisticsDetailReader() {
        MongoCursorItemReader<Statistics> itemReader = new MongoCursorItemReader<>();
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(),
                endDate.getDayOfMonth() - 1, 0, 0);

        Query query = new Query();
        query.addCriteria(Criteria.where("createdDate").gte(startDate).lt(endDate));

        itemReader.setBatchSize(100);
        itemReader.setTemplate(mongoTemplate);
        itemReader.setCollection("statistics");
        itemReader.setQuery(query);
        itemReader.setTargetType(Statistics.class);
        itemReader.afterPropertiesSet();

        return itemReader;
    }

    @Bean
    public ItemProcessor<Statistics, Statistics> computeStatisticsDetailProcessor() throws Exception {
        return item -> {
            log.info("COMPUTE PROCESSOR DETAIL {}", item.getApiKey());
            ExecutionContext context = stepExecution.getExecutionContext();
            StatisticsDetail detail = (StatisticsDetail) context.get(EXECUTION_PREFIX + item.getApiKey());

            if (detail == null) {
                detail = StatisticsDetail.builder()
                        .month(item.getCreatedDate().getMonthValue())
                        .apiKey(item.getApiKey())
                        .perApiInfo(new ArrayList<>())
                        .build();
            }

            detail.addPerApiInfo(item.getApi());
            context.put(EXECUTION_PREFIX + item.getApiKey(), detail);

            log.info("{}", detail.getPerApiInfo().size());
            return null;
        };
    }

    @Bean
    public MongoItemWriter<Statistics> computeStatisticsDetailWriter()
            throws Exception {
        MongoItemWriter<Statistics> itemWriter = new MongoItemWriter<>();
        itemWriter.setCollection("statisticsDetail");
        itemWriter.setTemplate(mongoTemplate);
        itemWriter.setMode(Mode.UPSERT);
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                setStepExecution(stepExecution);
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                stepExecution.getExecutionContext().entrySet().forEach(data -> {
                    if (data.getKey().contains(EXECUTION_PREFIX)) {
                        log.info("SAVE DETAIL {} ", data.getKey());
                        StatisticsDetail detail = (StatisticsDetail) data.getValue();
                        mongoTemplate.save(detail);
                    }
                });

                return ExitStatus.COMPLETED;
            }
        };
    }
}
