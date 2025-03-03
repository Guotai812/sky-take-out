package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.properties.LocalUploadProperties;
import com.sky.utils.UpLoadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UploadConfig {

    @Bean
    @ConditionalOnMissingBean
    public UpLoadUtil upLoadUtil(LocalUploadProperties localUploadProperties, AliOssProperties aliOssProperties) {
        log.info("创建上传工具类, {}", localUploadProperties);
        return new UpLoadUtil(localUploadProperties.getUploadPath(),
                localUploadProperties.getHostPath());
    }
}
