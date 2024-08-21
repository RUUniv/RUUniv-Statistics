package com.ruunivstatisticsserver.app.statistics.service;

import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public void getStatisticsInfo() {

    }

    @Transactional
    public void collectionStatistics(String apiUrl, String method, int status, String apiKey) {
        Statistics statistics = Statistics.builder()
                .api(Api.createByUrlAndMethod(apiUrl, method))
                .method(Method.valueOf(method))
                .status(status)
                .apiKey(apiKey)
                .build();

        statisticsRepository.save(statistics);
    }
}
