package com.gestion.comercial.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class RestCotizacionConfig {

    @Value("${app.sendgrid.apiKey}")
    private String sendGridApiKey;

   @Bean
   public RestTemplate restTemplate() {
        return new RestTemplate();
   }
   @Bean
   public SendGrid sendGrid() {
       return new SendGrid(sendGridApiKey);
   }
}
