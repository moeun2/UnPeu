package com.unpeu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UnpeuApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnpeuApplication.class, args);
	}

}
