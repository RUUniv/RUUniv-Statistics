package com.ruunivstatisticsserver.app.statistics.dto;

import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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

        public static StatisticsInfo from(Statistics statistics) {
            return StatisticsInfo.builder()
                    .api(statistics.getApi())
                    .status(statistics.getStatus())
                    .method(statistics.getMethod())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class StatisticsMonthInfo {
        private String apiKey;
        private int month;
        private List<StatisticsStatusPerApiMonthInfo> statisticsMonthInfo;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class StatisticsStatusPerApiMonthInfo {
        @Schema(description = "API 종류", example = "CREATE_VERIFICATION")
        private Api api;

        @Schema(description = "api 총 요청 수", example = "100")
        private int apiRequestTotalCount;
    }
}
