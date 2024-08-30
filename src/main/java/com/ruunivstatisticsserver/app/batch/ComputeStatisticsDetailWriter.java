package com.ruunivstatisticsserver.app.batch;

import com.ruunivstatisticsserver.app.statistics.entity.StatisticsDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComputeStatisticsDetailWriter implements ItemWriter<StatisticsDetail> {

    @Override
    public void write(Chunk<? extends StatisticsDetail> chunk) throws Exception {

    }
}
