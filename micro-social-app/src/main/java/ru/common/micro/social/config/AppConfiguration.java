package ru.common.micro.social.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@EnableScheduling
public class AppConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/news").setViewName("news");
    }

    @Primary
    @Bean(name = "dataSourceMaster")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSourceMaster() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dataSourceSlave1")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource dataSourceSlave1() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dataSourceSlave2")
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSource dataSourceSlave2() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplateMaster(@Qualifier("dataSourceMaster") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplateSlave1(@Qualifier("dataSourceSlave1") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplateSlave2(@Qualifier("dataSourceSlave2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
