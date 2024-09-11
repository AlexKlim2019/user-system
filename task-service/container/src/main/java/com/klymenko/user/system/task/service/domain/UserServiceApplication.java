package com.klymenko.user.system.task.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.klymenko.user.system.task.service.dao"})
@EntityScan(basePackages = {"com.klymenko.user.system.task.service.dao"})
@SpringBootApplication(scanBasePackages = "com.klymenko.user.system")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }
}
