package com.flightreservation.config;


import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AuditorConfig {
	
	@Bean
	AuditorAware<String> auditorProvider() {
		return () -> Optional.of("system");
	}

}
