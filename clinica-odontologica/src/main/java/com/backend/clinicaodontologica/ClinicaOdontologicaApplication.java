package com.backend.clinicaodontologica;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ClinicaOdontologicaApplication {

	private static Logger logger = LoggerFactory.getLogger(ClinicaOdontologicaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontologicaApplication.class, args);
		logger.info("ClinicaOdontologica is now running...");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}