package com.aws.pipeline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Configuration
public class OpenAIConfig implements WebMvcConfigurer {

    @Value("${openai.api.key}")
    private String openaiApiKey;

  /*  @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.interceptors((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        }).build();
    }
*/
  @Bean
  public RestTemplate template() {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getInterceptors().add((request, body, execution) -> {
          request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
          System.out.println("Request URL: " + request.getURI());
          System.out.println("Request Headers: " + request.getHeaders());
          System.out.println("Request Body: " + new String(body));
          return execution.execute(request, body);
      });
      return restTemplate;
  }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/bot/**")
                .allowedOrigins("http://localhost:8080")  // Allow all origins for testing
                .allowedMethods("GET", "POST");
        System.out.println("CORS configuration loaded.");
    }
}
