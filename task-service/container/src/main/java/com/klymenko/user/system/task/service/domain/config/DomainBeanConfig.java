package com.klymenko.user.system.task.service.domain.config;

import com.klymenko.user.system.task.service.domain.service.TaskDomainService;
import com.klymenko.user.system.task.service.domain.service.UserDomainService;
import com.klymenko.user.system.task.service.domain.service.impl.TaskDomainServiceImpl;
import com.klymenko.user.system.task.service.domain.service.impl.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeanConfig {

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

    @Bean
    public TaskDomainService taskDomainService() {
        return new TaskDomainServiceImpl();
    }
}
