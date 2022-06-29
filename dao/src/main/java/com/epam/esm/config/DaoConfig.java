package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;

/**
 * Representing dao configuration: dataSource and JdbcTemplate
 */
@Configuration
@ComponentScan("com.epam.esm")
public class DaoConfig {
    private ConfigurableEnvironment environment;

    @Autowired
    public DaoConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Bean
    @Profile("dev")
    public HikariConfig hikariConfig() throws IOException {
        environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:dev-connection.properties"));
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("url"));
        hikariConfig.setUsername(environment.getProperty("username"));
        hikariConfig.setPassword(environment.getProperty("password"));
        hikariConfig.setDriverClassName(environment.getProperty("className"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("poolSize"))));
        return hikariConfig;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("prod")
    public DataSource embeddedDataSource() throws IOException {
        environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:prod-connection.properties"));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("username"));
        dataSource.setPassword(environment.getProperty("password"));
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("className")));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
