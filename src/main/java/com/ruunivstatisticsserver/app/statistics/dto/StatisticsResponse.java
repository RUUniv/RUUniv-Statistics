package com.ruunivstatisticsserver.app.statistics.dto;

import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class StatisticsResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class StatisticsInfo {
        @Schema(description = "HTTP METHOD", example = "GET")
        private Method method;

        @Schema(description = "API 종류", example = "CREATE_VERIFICATION")
        private Api api;

        @Schema(description = "응답코드", example = "200")
        private int status;

        public static StatisticsInfo of(Statistics statistics) {
            return StatisticsInfo.builder()
                    .api(statistics.getApi())
                    .status(statistics.getStatus())
                    .method(statistics.getMethod())
                    .build();
        }
    }
}
