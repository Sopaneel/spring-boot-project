package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	private DataSourceProperties properties() {
		return new DataSourceProperties();
	}
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return properties().initializeDataSourceBuilder().type(MysqlDataSource.class).build();
	}
	
}
