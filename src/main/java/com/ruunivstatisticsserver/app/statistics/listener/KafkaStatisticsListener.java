package com.ruunivstatisticsserver.app.statistics.listener;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsEvent.CollectStatisticsEvent;
import com.ruunivstatisticsserver.app.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaStatisticsListener {
    private final StatisticsService statisticsService;

    @KafkaListener(topics = "collect-statistics")
    public void handleCollectStatistics(CollectStatisticsEvent event) {
        statisticsService.collectionStatistics(event.getApiUrl(), event.getMethod(),
                event.getStatus(), event.getApiKey());
    }
}
