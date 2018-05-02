package com.mikelduke.springsitebuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataGenerator {
	
	@Value("${sssg.testdata.generate:false}")
	boolean generateTestData;
	
	// @Bean
	// @Order(1)
	// public CommandLineRunner generateTestUsers() {
	// 	return (args) -> {
			
	// 	};
	// }
}
