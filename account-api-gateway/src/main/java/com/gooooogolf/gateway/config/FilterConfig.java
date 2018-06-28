package com.gooooogolf.gateway.config;

import com.gooooogolf.gateway.filter.SimpleFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public SimpleFilter simpleFilter() {
        return new SimpleFilter();
    }
}
