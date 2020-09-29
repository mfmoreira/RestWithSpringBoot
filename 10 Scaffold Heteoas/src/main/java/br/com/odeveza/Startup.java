package br.com.odeveza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration // allow the application content to auto load after the beans have been registered
@ComponentScan // scan packages and find configuration files
public class Startup {
	
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

}
