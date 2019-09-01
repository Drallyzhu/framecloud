package com.framecloud.log;

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
@RefreshScope
@ComponentScan(basePackages = {"com.framecloud.log", "com.framecloud.common"})
@EnableMethodCache(basePackages = "com.framecloud.log")
@EnableCreateCacheAnnotation
public class FramecloudLogApplication {

    public static void main(String[] args) {
        //不加这个，es启动报错
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(FramecloudLogApplication.class, args);
    }

}
