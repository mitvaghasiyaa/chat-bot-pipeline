package com.aws.pipeline.controller;

import com.aws.pipeline.dto.ChatGPTRequest;
import com.aws.pipeline.dto.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;



    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt){
        try {
            ChatGPTRequest request = new ChatGPTRequest(model, prompt);
            ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        } catch (HttpClientErrorException.Unauthorized e) {
            // Log or handle the Unauthorized exception
            e.printStackTrace();
            return "Unauthorized: " + e.getMessage();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}


