package com.neobis.neoCafe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.neobis.neoCafe")
@ComponentScan(basePackages = {"com.neobis.neoCafe", "com.neobis.neoCafe.mapper"})
public class NeoCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoCafeApplication.class, args);
	}

}
