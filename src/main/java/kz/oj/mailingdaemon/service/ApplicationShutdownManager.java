package kz.oj.mailingdaemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdownManager {

    @Autowired
    private ApplicationContext applicationContext;

    public void initiateShutdown() {
        SpringApplication.exit(applicationContext, () -> 0);
    }
}
