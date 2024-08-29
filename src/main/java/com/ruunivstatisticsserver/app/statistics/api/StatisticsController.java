package com.ruunivstatisticsserver.app.statistics.api;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsInfo;
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
        StatisticsInfo statisticsInfo = statisticsService.collectionStatistics("/v1/verification/univ", "GET", 500,
                "asd");
        log.info("{} {} {}", statisticsInfo.getApi(), statisticsInfo.getStatus(), statisticsInfo.getMethod());

    }

    @GetMapping("/{apiKey}")
    public ResponseEntity<StatisticsMonthInfo> getStatisticsMonthInfo(@PathVariable String apiKey,
                                                                      @RequestParam int month) {
        StatisticsMonthInfo statisticsInfoByMonth = statisticsService.getStatisticsInfoByMonth(apiKey, month);

        return ResponseEntity.ok(statisticsInfoByMonth);
    }
}
