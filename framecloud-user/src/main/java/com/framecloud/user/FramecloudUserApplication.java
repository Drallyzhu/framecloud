package com.framecloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.framecloud.user", "com.framecloud.common"})
public class FramecloudUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FramecloudUserApplication.class, args);
    }

}
