package com.ruunivstatisticsserver.app.statistics.service;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsInfo;
import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatisticsService {
    private final MongoTemplate mongoTemplate;

    public List<StatisticsInfo> getStatisticsInfoByMonth(String apiKey, int month) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        Query query = new Query();
        query.addCriteria(Criteria.where("apiKey").is(apiKey));
        query.addCriteria(Criteria.where("createdDate").gte(startDate).lt(endDate));

        List<Statistics> statistics = mongoTemplate.find(query, Statistics.class);

        return statistics.stream().map(StatisticsInfo::of).toList();
    }

    @Transactional
    public StatisticsInfo collectionStatistics(String apiUrl, String method, int status, String apiKey) {
        Statistics statistics = Statistics.builder()
                .api(Api.createByUrlAndMethod(apiUrl, method))
                .method(Method.valueOf(method))
                .status(status)
                .apiKey(apiKey)
                .build();

        return StatisticsInfo.of(mongoTemplate.save(statistics, "statistics"));
    }
}
