package com.example.pknk.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "orthanc")
@Getter
@Setter
public class OrthancConfig {
    
    /**
     * Orthanc server base URL
     * Default: http://localhost:8042
     */
    private String baseUrl = "http://localhost:8042";
    
    /**
     * Orthanc username for authentication (optional)
     */
    private String username;
    
    /**
     * Orthanc password for authentication (optional)
     */
    private String password;
    
    /**
     * Timeout for HTTP requests to Orthanc (in milliseconds)
     * Default: 30000 (30 seconds)
     */
    private int timeout = 30000;
    
    /**
     * Enable/disable Orthanc integration
     * Default: true
     */
    private boolean enabled = true;
    
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // Use timeout from configuration, default to 5 minutes (300000ms) for large files
        int configuredTimeout = timeout > 0 ? timeout : 300000;
        factory.setConnectTimeout(configuredTimeout);
        factory.setReadTimeout(configuredTimeout);
        return new RestTemplate(factory);
    }
}

