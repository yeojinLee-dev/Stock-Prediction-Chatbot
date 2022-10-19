package com.StockPredictionChatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StockPredictionChatbotApplication {

	public static void main(String[] args) {
		// https://youtu.be/aIg1Bcm7iB4
		SpringApplication.run(StockPredictionChatbotApplication.class, args);
		System.out.println("PROGRAM START\n");
	}

}
