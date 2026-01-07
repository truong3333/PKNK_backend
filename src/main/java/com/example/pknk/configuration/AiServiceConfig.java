package com.example.pknk.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "ai-service")
@Getter
@Setter
public class AiServiceConfig {
    
    /**
     * AI Service base URL
     * Default: http://localhost:8000
     */
    private String baseUrl = "http://localhost:8000";
    
    /**
     * Timeout for HTTP requests to AI Service (in milliseconds)
     * Default: 60000 (60 seconds)
     */
    private int timeout = 60000;
    
    /**
     * Enable/disable AI Service integration
     * Default: true
     */
    private boolean enabled = true;
    
    /**
     * Default confidence threshold for AI analysis
     * Default: 0.25
     */
    private double defaultConfidenceThreshold = 0.25;
    
    @Bean(name = "aiServiceRestTemplate")
    public RestTemplate aiServiceRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        int configuredTimeout = timeout > 0 ? timeout : 60000;
        factory.setConnectTimeout(configuredTimeout);
        factory.setReadTimeout(configuredTimeout);
        return new RestTemplate(factory);
    }
}

