package com.ruunivstatisticsserver.common.error.statistics;

import com.ruunivstatisticsserver.common.error.EntityNotFoundException;
import com.ruunivstatisticsserver.common.error.ErrorCode;

public class StatisticsDetailNotFoundException extends EntityNotFoundException {
    public StatisticsDetailNotFoundException() {
        super(ErrorCode.STATISTICS_DETAIL_NOT_FOUND);
    }
}
