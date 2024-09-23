package com.ruunivstatisticsserver.app.statistics.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsEvent.CollectStatisticsEvent;
import com.ruunivstatisticsserver.app.statistics.service.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaStatisticsListener {
    private final StatisticsService statisticsService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "COLLECT_STATISTICS")
    public void handleCollectStatistics(String payload) throws JsonProcessingException {

        log.info("[Kafka Listener] : COLLECT_STATISTICS START");
        CollectStatisticsEvent event = objectMapper.readValue(payload, CollectStatisticsEvent.class);

        statisticsService.collectStatistics(event.getApiUrl(), event.getMethod(),
                event.getStatus(), event.getApiKey());
    }
}
