package com.midas.midas_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MidasProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidasProjectApplication.class, args);
	}

}
