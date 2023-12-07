package com.aws.pipeline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenAIConfig implements WebMvcConfigurer {

    @Value("${openai.api.key}")
     String openaiApiKey;

    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }



        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/bot/**")
                    .allowedOrigins("http://localhost:8080")
                    .allowedMethods("GET", "POST");
        }
    }

