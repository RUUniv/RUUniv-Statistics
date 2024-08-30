package com.ruunivstatisticsserver.app.batch;

import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComputeStatisticsDetailReader implements ItemReader<StatisticsDetail> {
    private final MongoTemplate mongoTemplate;

    @Override
    public StatisticsDetail read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
