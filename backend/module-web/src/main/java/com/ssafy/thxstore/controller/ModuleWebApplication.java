package com.ssafy.thxstore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ssafy.thxstore")
public class ModuleWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(ModuleWebApplication.class, args);
	}
}
