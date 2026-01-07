package com.example.pknk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PknkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PknkApplication.class, args);
	}

}
