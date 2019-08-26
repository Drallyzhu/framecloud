package com.framecloud.auth.config;

import com.framecloud.auth.mobile.MobileAuthenticationFilter;
import com.framecloud.auth.mobile.MobileAuthenticationProvider;
import com.framecloud.auth.service.SysUserService;
import com.framecloud.common.config.IgnoreUrlPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @description: web security配置
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IgnoreUrlPropertiesConfig ignoreUrlPropertiesConfig;
    @Autowired
    private UserDetailsService userDetailsService;

    //认证成功处理
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    //认证失败处理
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 自定义验证管理器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setSysUserService(sysUserService);
        mobileAuthenticationProvider.setRedisTemplate(redisTemplate);
        return mobileAuthenticationProvider;
    }


    /**
     * 验证管理器（底层也是AuthenticationProvider验证）
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 自定义登陆过滤器
     */
    @Bean
    public MobileAuthenticationFilter mobileAuthenticationFilter() {
        MobileAuthenticationFilter filter = new MobileAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return filter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(mobileAuthenticationProvider()) //添加自定义认证服务器
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config
                = http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests();

        ignoreUrlPropertiesConfig.getUrls().forEach(e -> {
            config.antMatchers(e).permitAll();
        });

        config
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
//                .and()
//               .headers().frameOptions().disable()
//                    .and().formLogin().permitAll()
                .and().csrf().disable();
    }


}
