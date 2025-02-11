package com.harajuku.messagingApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		try {
			SpringApplication.run(App.class, args);
		} catch (Exception e) {
			logger.warn("APPLICATON START THREW EXCEPTION: " + e.getClass().getName());
			e.printStackTrace();
		}
	}

}
