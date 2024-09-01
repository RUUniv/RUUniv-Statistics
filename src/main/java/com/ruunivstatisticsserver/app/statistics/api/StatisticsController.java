package com.ruunivstatisticsserver.app.statistics.api;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MongoTemplate mongoTemplate;

    @GetMapping("/{apiKey}")
    public ResponseEntity<StatisticsMonthInfo> getStatisticsMonthInfo(@PathVariable String apiKey,
                                                                      @RequestParam int month) {
        StatisticsMonthInfo response = statisticsService.getStatisticsInfoByMonth(apiKey, month);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public void getStatisticsMonthInfo() {
        LocalDate s = LocalDate.now().minusMonths(1);
        LocalDate e = LocalDate.now();

        Query query = new Query();
        query.addCriteria(Criteria.where("createdDate").gte(s).lt(e).
                and("apiKey").is("asdf")
        );

        mongoTemplate.find(query, Statistics.class);

    }
}
