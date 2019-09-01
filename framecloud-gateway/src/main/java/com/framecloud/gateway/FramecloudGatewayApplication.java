package com.framecloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.framecloud.gateway", "com.framecloud.common", "com.framecloud.gateway.auth"})
@EnableZuulProxy
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FramecloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FramecloudGatewayApplication.class, args);
    }

}
