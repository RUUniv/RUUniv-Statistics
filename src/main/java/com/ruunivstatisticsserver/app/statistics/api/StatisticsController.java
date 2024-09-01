package com.ruunivstatisticsserver.app.statistics.api;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "통계", description = "통계 정보를 조회 및 관리 합니다")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final JobLauncher jobLauncher;
    private final Job job;
    private final MongoTemplate mongoTemplate;

    @GetMapping()
    public void test()
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(),
                endDate.getDayOfMonth() - 1, 0, 0);
        
        Query query = new Query();
        query.addCriteria(Criteria.where("createdDate").gte(startDate).lt(endDate));

        List<Statistics> statistics = mongoTemplate.find(query, Statistics.class);
        log.info("{}", endDate);
        log.info("{}", startDate);
        log.info("{}", statistics.size());
//        statistics.forEach(data -> log.info("d {}", data.getCreatedDate()));
        log.info("d {}", statistics.get(500).getCreatedDate());
        jobLauncher.run(job, new JobParameters());
    }

    @GetMapping("/{apiKey}")
    public ResponseEntity<StatisticsMonthInfo> getStatisticsMonthInfo(@PathVariable String apiKey,
                                                                      @RequestParam int month) {
        long l = System.currentTimeMillis();
        StatisticsMonthInfo statisticsInfoByMonth = statisticsService.getStatisticsInfoByMonth(apiKey, month);
        long l2 = System.currentTimeMillis();

        log.info("{}", (l2 - l) / 1000);
        return ResponseEntity.ok(statisticsInfoByMonth);
    }
}
