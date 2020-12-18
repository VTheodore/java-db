package com.vezenkov.productsshop.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vezenkov.productsshop.util.FileIOUtil;
import com.vezenkov.productsshop.util.impl.FileIOUtilImpl;
import com.vezenkov.productsshop.util.ValidationUtil;
import com.vezenkov.productsshop.util.impl.ValidationUtilImpl;
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
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
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
