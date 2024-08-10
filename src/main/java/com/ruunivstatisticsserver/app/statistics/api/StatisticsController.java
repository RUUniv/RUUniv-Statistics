package com.ruunivstatisticsserver.app.statistics.api;

import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    private final StatisticsRepository statisticsRepository;

    @GetMapping
    public void test(){
        Statistics test = Statistics.builder()
                .apiKey("asd")
                .build();

        statisticsRepository.save(test);
    }
}
