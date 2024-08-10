package com.ruunivstatisticsserver.app.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StatisticsEvent {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CollectStatisticsEvent {
        private String apiUrl;
        private String method;
        private int status;
        private String apiKey;
    }
}
