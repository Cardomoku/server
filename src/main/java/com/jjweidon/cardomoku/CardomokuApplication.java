package com.jjweidon.cardomoku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CardomokuApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardomokuApplication.class, args);
	}

}
