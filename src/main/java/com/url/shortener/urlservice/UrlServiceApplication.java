package com.url.shortener.urlservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlServiceApplication.class, args);
	}

    @EnableRabbit
    @SpringBootApplication
    public class AnalyticsServiceApplication {
        public static void main(String[] args) {
            SpringApplication.run(AnalyticsServiceApplication.class, args);
        }
    }
}
