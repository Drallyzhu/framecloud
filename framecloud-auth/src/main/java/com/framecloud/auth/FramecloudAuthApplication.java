package com.framecloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.framecloud.auth", "com.framecloud.common"})
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class FramecloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FramecloudAuthApplication.class, args);
    }

}
