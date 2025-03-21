package com.ibrahim.Cybersecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.ibrahim.Cybersecurity.repository")
@EnableTransactionManagement
public class DatabaseConfig {
}