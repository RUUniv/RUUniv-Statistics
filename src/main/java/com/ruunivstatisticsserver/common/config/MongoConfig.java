package com.ruunivstatisticsserver.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {
    private final DateToLocalDateTimeKstConverter dateToLocalDateTimeKstConverter;
    private final LocalDateTimeToDateKstConverter localDateTimeToDateKstConverter;

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(dateToLocalDateTimeKstConverter, localDateTimeToDateKstConverter));
    }
}
