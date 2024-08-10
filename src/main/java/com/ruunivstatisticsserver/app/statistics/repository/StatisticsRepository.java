package com.ruunivstatisticsserver.app.statistics.repository;

import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface StatisticsRepository extends MongoRepository<Statistics,String> {
}
