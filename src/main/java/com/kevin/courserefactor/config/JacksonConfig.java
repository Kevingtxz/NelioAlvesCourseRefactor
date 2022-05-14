package com.kevin.courserefactor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.courserefactor.base.domain.PaymentWithCard;
import com.kevin.courserefactor.base.domain.PaymentWithTicket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PaymentWithCard.class);
                objectMapper.registerSubtypes(PaymentWithTicket.class);
                super.configure(objectMapper);
            };
        };
        return builder;
    }
}