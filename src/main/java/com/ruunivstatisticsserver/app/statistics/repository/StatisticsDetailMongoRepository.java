package com.ruunivstatisticsserver.app.statistics.repository;

import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
import com.ruunivstatisticsserver.common.error.statistics.StatisticsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatisticsDetailMongoRepository {
    private final MongoTemplate mongoTemplate;

    public StatisticsDetail findByApiKeyAndMonth(String apiKey, int month) {
        Query query = new Query();
        query.addCriteria(Criteria.where("apiKey").is(apiKey)
                .and("month").is(month)
        );

        StatisticsDetail detail = mongoTemplate.findOne(query, StatisticsDetail.class);

        if (detail == null) {
            throw new StatisticsNotFoundException();
        }

        return detail;
    }
}
