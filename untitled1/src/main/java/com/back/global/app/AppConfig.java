package com.back.global.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.back.domain.wiseSaying.repository")
@EntityScan(basePackages = "com.back.domain.wiseSaying.entity")
@ComponentScan(basePackages = "com.back")
public class AppConfig {
}

