package com.ruunivstatisticsserver.app.statistics.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatisticsMongoCustomRepository {
    private final MongoTemplate mongoTemplate;
}
