package com.ruunivstatisticsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.ruunivstatisticsserver.app.statistics.repository"})
@EnableMongoAuditing
@EnableScheduling
public class RuunivStatisticsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuunivStatisticsServerApplication.class, args);
    }

}

