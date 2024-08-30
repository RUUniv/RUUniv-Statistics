package com.ruunivstatisticsserver.app.statistics.api;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;
import com.ruunivstatisticsserver.app.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "통계", description = "통계 정보를 조회 및 관리 합니다")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping()
    public void test() {
        for (int i = 0; i < 100; i++) {
            statisticsService.collectionStatistics("/v1/verification/email", "GET", 200, "asdf");
        }

    }

    @GetMapping("/{apiKey}")
    public ResponseEntity<StatisticsMonthInfo> getStatisticsMonthInfo(@PathVariable String apiKey,
                                                                      @RequestParam int month) {
        long l = System.currentTimeMillis();
        StatisticsMonthInfo statisticsInfoByMonth = statisticsService.getStatisticsInfoByMonth(apiKey, month);
        long l2 = System.currentTimeMillis();

        log.info("{}", (l2 - l) / 1000);
        return ResponseEntity.ok(statisticsInfoByMonth);
    }
}
