package com.fredrick.financial_management.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnviromentLogger {
    @Value("${MYSQLHOST}")
    private String mysqlHost;

    @Value("${MYSQLPORT}")
    private String mysqlPort;

    @Value("${MYSQLUSER}")
    private String mysqlUser;

    @Value("${MYSQLPASSWORD}")
    private String mysqlPassword;

    @Value("${MYSQLDATABASE}")
    private String mysqlDatabase;
    @Value("${MYSQL_URL}")
    private String MYSQL_URL;
    @Value("${MYSQL_ROOT_PASSWORD}")
    private String MYSQL_ROOT_PASSWORD;
    @Value("${MYSQL_DATABASE}")
    private String MYSQL_DATABASE;
    @Value("${MYSQL_PUBLIC_URL}")
    private String MYSQL_PUBLIC_URL;

    @PostConstruct
    public void logEnvironment() {
        System.out.println("MYSQL_HOST: " + mysqlHost);
        System.out.println("MYSQL_PORT: " + mysqlPort);
        System.out.println("MYSQL_USER: " + mysqlUser);
        System.out.println("MYSQL_PASSWORD: " + mysqlPassword);
        System.out.println("MYSQL_URL: " + MYSQL_URL);
        System.out.println("MYSQL_ROOT_PASSWORD: " + MYSQL_ROOT_PASSWORD);
        System.out.println("MYSQL_DATABASE: " + mysqlDatabase);
        System.out.println("MYSQL_PUBLIC_URL: " + MYSQL_PUBLIC_URL);
        System.out.println("MYSQLDATABASE: " + mysqlDatabase);

    }
}
