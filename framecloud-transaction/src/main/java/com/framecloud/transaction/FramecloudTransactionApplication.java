package com.framecloud.transaction;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.framecloud.transaction")
@ComponentScan({"com.framecloud.transaction", "com.framecloud.common"})
public class FramecloudTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FramecloudTransactionApplication.class, args);
    }

}
