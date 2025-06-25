package com.example.javapro.config;


import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Bean
    @ConfigurationProperties("application.main-datasource")
    public DataSourceProperties datasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mainDataSource() {
        final var prop = datasourceProperties();
        final var dataSource = new PGSimpleDataSource();
        dataSource.setUrl(prop.getUrl());
        dataSource.setUser(prop.getUsername());
        dataSource.setPassword(prop.getPassword());
        return dataSource;
    }
}