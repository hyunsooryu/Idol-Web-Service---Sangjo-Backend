package com.idol.idolprojectbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idol.idolprojectbackend.interceptor.JsonInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration reg = registry.addInterceptor(new JsonInterceptor(objectMapper()));
        reg.addPathPatterns("/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration reg1 = registry.addResourceHandler("img/**");
        reg1.addResourceLocations("classpath:/images/");
        reg1.setCacheControl(CacheControl.maxAge(Duration.ofMinutes(60)));

        ResourceHandlerRegistration reg2 = registry.addResourceHandler("css/**");
        reg2.addResourceLocations("classpath:/css/");
        reg2.setCacheControl(CacheControl.maxAge(Duration.ofMinutes(60)));

        ResourceHandlerRegistration reg3 = registry.addResourceHandler("js/**");
        reg3.addResourceLocations("classpath:/js/");
        reg3.setCacheControl(CacheControl.noCache());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/img/**").allowedOrigins("*");
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
