/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CustomPollerConfiguration.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.framecloud.log.config;

import org.springframework.context.annotation.Configuration;

/**
 * The class Custom poller configuration.
 *
 * @author paascloud.net@gmail.com
 */
@Configuration
public class CustomPollerConfiguration {
    /**
     * Custom poller poller metadata.
     *
     * @return the poller metadata
     */
	/*@Bean(name = StreamSpanReporter.POLLER)
    PollerMetadata customPoller() {
		PollerMetadata poller = new PollerMetadata();
		poller.setMaxMessagesPerPoll(500);
		poller.setTrigger(new PeriodicTrigger(5000L));
		return poller;
	}*/

//	@Bean
//	public Sampler defaultSampler() {
//		return  new AlwaysSampler();
//	}
}
