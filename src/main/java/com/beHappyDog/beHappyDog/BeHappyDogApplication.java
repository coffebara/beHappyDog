package com.beHappyDog.beHappyDog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeHappyDogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeHappyDogApplication.class, args);
	}

}
