package com.ruunivstatisticsserver.common.error.statistics;

import com.ruunivstatisticsserver.common.error.EntityNotFoundException;
import com.ruunivstatisticsserver.common.error.ErrorCode;

public class StatisticsNotFoundException extends EntityNotFoundException {
    public StatisticsNotFoundException() {
        super(ErrorCode.STATISTICS_NOT_FOUND);
    }
}
