package com.ruunivstatisticsserver.app.statistics.entity;

import com.ruunivstatisticsserver.common.BaseTimeEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StatisticsDetail extends BaseTimeEntity {

    @Id
    private String id;
    private String apiKey;
    private int month;
    private List<StatisticsDetailPerApiInfo> perApiInfo;
}
