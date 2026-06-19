package com.demo.app;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder; // Corrected import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource") // No need for DataSourceProperties.PREFIX here
    DataSource realDataSource() {
        // DataSourceBuilder.create() is a static method that returns a new builder instance.
        // It's the correct way to create the builder.
        DataSourceBuilder<?> builder = DataSourceBuilder.create(this.dataSourceProperties.getClassLoader()); // Corrected usage

        // Apply properties from DataSourceProperties
        builder.url(this.dataSourceProperties.getUrl());
        builder.username(this.dataSourceProperties.getUsername());
        builder.password(this.dataSourceProperties.getPassword());

        // This method also exists if you prefer to directly set the type
        // if (this.dataSourceProperties.getType() != null) {
        //     builder.type(this.dataSourceProperties.getType());
        // }

        DataSource dataSource = builder.build();
        return dataSource;
    }

    @Bean
    @Primary
    DataSource dataSource() {
        // Ensure you have a 'realDataSource()' method that returns a non-null DataSource
        // if this method relies on it. Spring will call realDataSource() to get the dependency.
        return new DataSourceSpy(realDataSource());
    }
}