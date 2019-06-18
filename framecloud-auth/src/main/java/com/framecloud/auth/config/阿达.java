package com.framecloud.auth.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class 阿达 {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("framecloud");
        System.out.println(password);
        System.out.println(passwordEncoder.matches("fisher","$2a$10$Z6IcA7mEzs6VhQM2.fjcO.NSMjBEwD7YqY6B3w/Im30eJOBUTyQPi"));
    }
}
