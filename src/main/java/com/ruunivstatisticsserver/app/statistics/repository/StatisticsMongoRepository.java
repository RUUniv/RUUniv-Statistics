package com.ruunivstatisticsserver.app.statistics.repository;

import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatisticsMongoRepository {
    private final MongoTemplate mongoTemplate;

    public Statistics save(Statistics statistics) {
        return mongoTemplate.save(statistics, "statistics");
    }
}
