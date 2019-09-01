package com.framecloud.user;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.framecloud.user", "com.framecloud.common"})
@EnableMethodCache(basePackages = "com.framecloud.user")
@EnableCreateCacheAnnotation
@RefreshScope
public class FramecloudUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FramecloudUserApplication.class, args);
    }

}
