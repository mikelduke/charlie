package com.mikelduke.charlie.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
    
    @Bean
    public ObjectMapper getObjectMapper() {
        YAMLFactory factory = new YAMLFactory()
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

        ObjectMapper mapper = new ObjectMapper(factory);
        mapper.setSerializationInclusion(Include.NON_NULL);
        
        return mapper;
    }
}