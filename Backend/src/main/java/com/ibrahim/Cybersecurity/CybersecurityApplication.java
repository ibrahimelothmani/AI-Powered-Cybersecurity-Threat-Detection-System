package com.ibrahim.Cybersecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CybersecurityApplication {
	private final static Logger log
			= LoggerFactory.getLogger(CybersecurityApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CybersecurityApplication.class, args);
		log.info("Cybersecurity application started successfully.");
	}

}
