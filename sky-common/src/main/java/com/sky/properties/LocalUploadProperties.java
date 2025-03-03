package com.sky.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.upload")
@Data
public class LocalUploadProperties {
    private String uploadPath;
    private String hostPath;
}
