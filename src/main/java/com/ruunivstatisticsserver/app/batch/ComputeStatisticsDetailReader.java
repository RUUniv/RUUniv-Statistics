package com.ruunivstatisticsserver.app.batch;

import com.ruunivstatisticsserver.app.statistics.entity.Statistics;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComputeStatisticsDetailReader implements ItemReader<List<Statistics>> {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Statistics> read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth() - 1);

        Query query = new Query();
        query.addCriteria(Criteria.where("createdDate").gte(startDate).lt(endDate));

        return mongoTemplate.find(query, Statistics.class);
    }
}
