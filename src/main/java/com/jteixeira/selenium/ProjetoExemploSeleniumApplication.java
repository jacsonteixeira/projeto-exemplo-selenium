package com.jteixeira.selenium;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jteixeira.selenium.runner.SeleniumRunner;

@SpringBootApplication
public class ProjetoExemploSeleniumApplication {

	@Bean
	public CommandLineRunner runner() {
		return new SeleniumRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjetoExemploSeleniumApplication.class, args);
	}

}
