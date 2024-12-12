package ru.bikmag.carsharing.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Позволяет все пути
                .allowedOrigins("http://localhost:3000") // Задает домен стороннего приложения
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Доступные методы
                .allowedHeaders("*"); // Доступные заголовки (* - все)
    }
}