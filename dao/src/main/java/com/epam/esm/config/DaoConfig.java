package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:${spring.profiles.active}-connection.properties")
public class DaoConfig {
    private ConfigurableEnvironment environment;

    @Autowired
    public DaoConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource(HikariConfig hikariConfig){
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("url"));
        hikariConfig.setUsername(environment.getProperty("username"));
        hikariConfig.setPassword(environment.getProperty("password"));
        hikariConfig.setDriverClassName(environment.getProperty("className"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("poolSize")));
        return hikariConfig;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
