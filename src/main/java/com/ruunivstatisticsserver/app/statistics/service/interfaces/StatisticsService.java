package com.ruunivstatisticsserver.app.statistics.service.interfaces;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsInfo;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;

public interface StatisticsService {
    StatisticsMonthInfo getStatisticsInfoByMonth(String apiKey, int month);

    StatisticsInfo collectionStatistics(String apiUrl, String method, int status, String apiKey);
}
