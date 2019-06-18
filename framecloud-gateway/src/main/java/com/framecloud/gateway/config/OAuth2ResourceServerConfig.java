package com.framecloud.gateway.config;


import com.framecloud.common.base.config.IgnoreUrlPropertiesConfig;
import com.framecloud.gateway.auth.endpoint.AuthExceptionEntryPoint;
import com.framecloud.gateway.auth.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @description: oauth2资源服务器配置
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private IgnoreUrlPropertiesConfig ignoreUrlPropertiesConfig;


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置token存储到redis中
     * @return
     */
    @Bean
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore store = new RedisTokenStore(redisConnectionFactory);
        return store;
    }

    //授权失败时返回信息
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;


    //解决跨域问题
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }




    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config =  http.authorizeRequests();
        ignoreUrlPropertiesConfig.getUrls().forEach( e ->{
            config.antMatchers(e).permitAll();
        });
        // 前后分离 先发出options 放行
        config.antMatchers(HttpMethod.OPTIONS,"/**","/auth/**","/admin/**","/actuator/**","/oauth/**").permitAll()
                .anyRequest().access("@permissionService.hasPermission(request,authentication)");

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(redisTokenStore())
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(new AuthExceptionEntryPoint())//用于token校验失败返回信息
                .expressionHandler(expressionHandler);
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }


}
