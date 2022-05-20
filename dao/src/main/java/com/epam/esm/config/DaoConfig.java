package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;

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
        hikariConfig.setPassword(environment.getProperty("pw"));
        hikariConfig.setDriverClassName(environment.getProperty("cn"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("ps")));
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
        dataSource.setPassword(environment.getProperty("pw"));
        dataSource.setDriverClassName(environment.getProperty("cn"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
