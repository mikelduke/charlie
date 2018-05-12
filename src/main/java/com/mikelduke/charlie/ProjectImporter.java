package com.mikelduke.charlie;

import com.mikelduke.charlie.services.ImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class ProjectImporter {
	
    @Value("${charlie.import.enabled:true}")
    boolean enableImport;

    @Autowired
    ImportService importService;

	@Bean
	@Order(1)
	public CommandLineRunner importProject() {
		return (args) -> {
			if (!enableImport) {
                return;
            }

			importService.importProject();
		};
	}
}
