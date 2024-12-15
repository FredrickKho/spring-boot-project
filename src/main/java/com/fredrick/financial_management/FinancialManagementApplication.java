package com.fredrick.financial_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class FinancialManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialManagementApplication.class, args);
	}

}
