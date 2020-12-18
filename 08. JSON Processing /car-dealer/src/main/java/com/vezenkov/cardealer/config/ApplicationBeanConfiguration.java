package com.vezenkov.cardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vezenkov.cardealer.util.DateDeserializer;
import com.vezenkov.cardealer.util.FileIOUtil;
import com.vezenkov.cardealer.util.ValidationUtil;
import com.vezenkov.cardealer.util.impl.FileIOUtilImpl;
import com.vezenkov.cardealer.util.impl.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public FileIOUtil fileIOUtil() {
        return new FileIOUtilImpl();
    }
}
