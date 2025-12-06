package com.example.pknk.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dn2plfafj",
                "api_key", "231853141218113",
                "api_secret", "VGunAooO69wpwRakYcRP7UXWmzI",
                "secure", true
        ));
    }
}
