package com.yaocoder.myset.CustomConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
public class WebMvcConfig{
//    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**").addResourceLocations("file:D:/projectData/UIData/");
    }
}
