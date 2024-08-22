package com.ruunivstatisticsserver.app.statistics.repository;

import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    List<Statistics> findAllByApiKey(String apiKey);
}
