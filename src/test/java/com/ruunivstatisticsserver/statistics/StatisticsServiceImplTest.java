package com.ruunivstatisticsserver.statistics;

import static com.ruunivstatisticsserver.statistics.StatisticsTestConstant.API_KEY;
import static com.ruunivstatisticsserver.statistics.StatisticsTestConstant.MONTH;
import static com.ruunivstatisticsserver.statistics.StatisticsTestConstant.STATUS;
import static com.ruunivstatisticsserver.statistics.StatisticsTestConstant.TOTAL_API_COUNT;

import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsInfo;
import com.ruunivstatisticsserver.app.statistics.dto.StatisticsResponse.StatisticsMonthInfo;
import com.ruunivstatisticsserver.app.statistics.entity.Api;
import com.ruunivstatisticsserver.app.statistics.entity.Method;
import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetailPerApiInfo;
import com.ruunivstatisticsserver.app.statistics.repository.StatisticsDetailMongoRepository;
import com.ruunivstatisticsserver.app.statistics.repository.StatisticsMongoRepository;
import com.ruunivstatisticsserver.app.statistics.service.StatisticsServiceImpl;
import com.ruunivstatisticsserver.common.error.statistics.StatisticsDetailNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceImplTest {
    @InjectMocks
    StatisticsServiceImpl statisticsService;

    @Mock
    StatisticsDetailMongoRepository statisticsDetailMongoRepository;

    @Mock
    StatisticsMongoRepository statisticsMongoRepository;

    StatisticsDetail statisticsDetail;

    Statistics statistics;

    StatisticsDetailPerApiInfo statisticsDetailPerApiInfo;

    @BeforeEach
    void BEFORE_EACH() {
        this.statisticsDetailPerApiInfo = StatisticsDetailPerApiInfo.builder()
                .api(Api.CREATE_VERIFICATION)
                .totalApiRequestCount(TOTAL_API_COUNT)
                .build();

        this.statisticsDetail = StatisticsDetail.builder()
                .apiKey(API_KEY)
                .month(MONTH)
                .perApiInfo(List.of(this.statisticsDetailPerApiInfo))
                .build();

        this.statistics = Statistics.builder()
                .status(STATUS)
                .method(Method.POST)
                .api(Api.CREATE_VERIFICATION)
                .apiKey(API_KEY)
                .build();
    }

    @Test
    @DisplayName("GET_STATISTICS_INFO_BY_MONTH_TEST")
    void GET_STATISTICS_INFO_BY_MONTH_SUCCESS_TEST() {

        BDDMockito.given(statisticsDetailMongoRepository.findByApiKeyAndMonth(API_KEY, MONTH))
                .willReturn(this.statisticsDetail);

        StatisticsMonthInfo response = statisticsService.getStatisticsInfoByMonth(API_KEY, MONTH);

        Assertions.assertEquals(API_KEY, response.getApiKey());
        Assertions.assertEquals(MONTH, response.getMonth());
        Assertions.assertEquals(Api.CREATE_VERIFICATION, response.getStatisticsMonthInfo().get(0).getApi());
        Assertions.assertEquals(TOTAL_API_COUNT, response.getStatisticsMonthInfo().get(0).getApiRequestTotalCount());
    }

    @Test
    @DisplayName("GET_STATISTICS_INFO_BY_MONTH_FAILURE_BY_NOT_FOUND_STATISTICS_DETAIL_TEST")
    void GET_STATISTICS_INFO_BY_MONTH_FAILURE_BY_NOT_FOUND_STATISTICS_DETAIL_TEST() {

        BDDMockito.given(statisticsDetailMongoRepository.findByApiKeyAndMonth(API_KEY, MONTH))
                .willThrow(new StatisticsDetailNotFoundException());

        Assertions.assertThrows(StatisticsDetailNotFoundException.class,
                () -> statisticsService.getStatisticsInfoByMonth(API_KEY, MONTH));
    }

    @Test
    @DisplayName("COLLECT_STATISTICS_TEST")
    void COLLECT_STATISTICS_TEST() {
        BDDMockito.given(statisticsMongoRepository.save(BDDMockito.any())).willReturn(this.statistics);

        StatisticsInfo response = statisticsService.collectStatistics(Api.CREATE_VERIFICATION.getUrl(),
                Api.CREATE_VERIFICATION.getMethod(),
                STATUS, API_KEY);

        Assertions.assertEquals(Api.CREATE_VERIFICATION, response.getApi());
        Assertions.assertEquals(STATUS, response.getStatus());
        Assertions.assertEquals(Method.POST, response.getMethod());
    }
}
