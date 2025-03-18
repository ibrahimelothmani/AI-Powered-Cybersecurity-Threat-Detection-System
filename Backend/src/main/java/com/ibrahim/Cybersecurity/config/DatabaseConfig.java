package com.ibrahim.Cybersecurity.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.ibrahim.Cybersecurity.repository"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = ".*MongoRepository"))
@EnableMongoRepositories(basePackages = "com.ibrahim.Cybersecurity.repository",
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = ".*MongoRepository"))
@EnableTransactionManagement
public class DatabaseConfig {
}