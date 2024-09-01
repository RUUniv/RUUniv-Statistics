package com.ruunivstatisticsserver.app.statistics.entity;

import com.ruunivstatisticsserver.common.BaseTimeEntity;
import java.io.Serializable;
import java.util.ArrayList;
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
public class StatisticsDetail extends BaseTimeEntity implements Serializable {

    @Id
    private String id;
    private String apiKey;
    private int month;
    private List<StatisticsDetailPerApiInfo> perApiInfo = new ArrayList<>();

    @Builder
    public StatisticsDetail(String apiKey, int month) {
        this.apiKey = apiKey;
        this.month = month;
        this.perApiInfo = new ArrayList<>();
    }

    public void addPerApiInfo(Api api) {
        StatisticsDetailPerApiInfo apiInfo = this.perApiInfo.stream()
                .filter(data -> data.getApi() == api).findFirst().orElse(null);

        if (apiInfo == null) {
            apiInfo = StatisticsDetailPerApiInfo.builder()
                    .api(api)
                    .build();

            this.perApiInfo.add(apiInfo);
        }

        apiInfo.addCount();
    }
}
