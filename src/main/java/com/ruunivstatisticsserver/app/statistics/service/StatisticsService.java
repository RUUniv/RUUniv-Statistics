package com.ruunivstatisticsserver.app.statistics.service;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsInfo;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsStatusPerApiMonthInfo;
import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
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

    public StatisticsMonthInfo getStatisticsInfoByMonth(String apiKey, int month) {

        Query query = new Query();
        query.addCriteria(Criteria.where("apiKey").is(apiKey)
                .and("month").is(month)
        );

        StatisticsDetail detail = mongoTemplate.findOne(query, StatisticsDetail.class);

        List<StatisticsStatusPerApiMonthInfo> perApiInfo = detail.getPerApiInfo().stream()
                .map(info -> new StatisticsStatusPerApiMonthInfo(info.getApi(), info.getTotalApiRequestCount()))
                .toList();

        return new StatisticsMonthInfo(detail.getApiKey(), detail.getMonth(), perApiInfo);
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
