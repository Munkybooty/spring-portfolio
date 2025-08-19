package com.hussain.projects.spring_portfolio;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPortfolioApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        // Push .env variables into system properties for Spring to resolve
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

		SpringApplication.run(SpringPortfolioApplication.class, args);
	}

}
