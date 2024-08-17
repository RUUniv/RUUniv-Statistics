package com.ruunivstatisticsserver.infrastructure.kafka.config;

import lombok.Getter;

@Getter
public enum KafkaConstant {
    KAFKA_TOPIC_COLLECT_STATISTICS("COLLECT_STATISTICS");

    private String topic;

    KafkaConstant(String collectStatistics) {

    }

}
