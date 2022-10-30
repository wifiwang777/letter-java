package com.wifi.letter.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileNotFoundException;

@Configuration
public class ResourceConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            String localPath = ResourceUtils.getURL("classpath:").getPath() + "static/assets/file/";
            System.out.println("localPath:" + localPath);
            registry.addResourceHandler("/file/**").addResourceLocations("file:" + localPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
