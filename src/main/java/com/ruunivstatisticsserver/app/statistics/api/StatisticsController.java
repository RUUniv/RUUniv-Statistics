package com.ruunivstatisticsserver.app.statistics.api;

import com.ruunivstatisticsserver.app.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "통계", description = "통계 정보를 조회 및 관리 합니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public void test() {
        statisticsService.collectionStatistics("asd", "GET", 500, "asd");
    }
}
