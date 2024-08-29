package com.ruunivstatisticsserver.app.statistics.service;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsInfo;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsStatusPerApiMonthInfo;
import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public StatisticsMonthInfo getStatisticsInfoByMonth(String apiKey, int month) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        Query query = new Query();
        query.addCriteria(Criteria.where("apiKey").is(apiKey));
        query.addCriteria(Criteria.where("createdDate").gte(startDate).lt(endDate));

        List<Statistics> statistics = mongoTemplate.find(query, Statistics.class);

        return computeStatisticsDetail(statistics);
    }

    private StatisticsMonthInfo computeStatisticsDetail(List<Statistics> statisticsData) {
        Map<Api, Integer> apiData = new HashMap<>();
        Arrays.stream(Api.values()).forEach(api -> apiData.put(api, 0));

        statisticsData.forEach(statistics -> {
            apiData.computeIfPresent(statistics.getApi(), (key, value) -> ++value);
        });

        List<StatisticsStatusPerApiMonthInfo> perApiInfo = apiData.entrySet().stream()
                .map(data -> new StatisticsStatusPerApiMonthInfo(data.getKey(), data.getValue()))
                .toList();

        return new StatisticsMonthInfo(perApiInfo);
    }

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
