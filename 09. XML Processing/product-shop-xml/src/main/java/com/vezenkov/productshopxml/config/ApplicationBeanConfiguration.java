package com.vezenkov.productshopxml.config;

import com.vezenkov.productshopxml.util.ValidationUtil;
import com.vezenkov.productshopxml.util.XMLParser;
import com.vezenkov.productshopxml.util.impl.ValidationUtilImpl;
import com.vezenkov.productshopxml.util.impl.XMLParserImpl;
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
    public XMLParser xmlParser(){
        return new XMLParserImpl();
    }
}
