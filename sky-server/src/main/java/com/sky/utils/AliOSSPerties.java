package com.sky.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/17 10:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSPerties {
    //    @Value("${aliyun.oss.endpoint}")
    private String endpoint ;
    //    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId ;
    //    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret ;
    //    @Value("${aliyun.oss.bucketName}")
    private String bucketName ;
}
