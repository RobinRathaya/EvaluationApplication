package com.chainsys.evaluationapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {
@Bean
public JdbcTemplate getJdbcTemplate(DataSource dataSource)
{
	return new JdbcTemplate(dataSource);
}
}
