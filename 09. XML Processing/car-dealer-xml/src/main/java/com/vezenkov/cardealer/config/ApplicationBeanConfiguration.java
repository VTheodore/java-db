package com.vezenkov.cardealer.config;

import com.vezenkov.cardealer.util.ValidationUtil;
import com.vezenkov.cardealer.util.XMLParser;
import com.vezenkov.cardealer.util.impl.ValidationUtilImpl;
import com.vezenkov.cardealer.util.impl.XMLParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public XMLParser fileIOUtil() {
        return new XMLParserImpl();
    }
}
