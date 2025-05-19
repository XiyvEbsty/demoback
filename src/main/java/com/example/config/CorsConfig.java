package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;

    @Value("${cors.max-age}")
    private long maxAge;

    @Bean
    public CorsFilter corsFilter() {
        System.out.println("初始化CORS过滤器");
        System.out.println("允许的域名: " + allowedOrigins);
        System.out.println("允许的方法: " + allowedMethods);
        System.out.println("允许的头: " + allowedHeaders);
        System.out.println("是否允许凭证: " + allowCredentials);
        
        CorsConfiguration config = new CorsConfiguration();
        
        // 设置允许的域名
        if ("*".equals(allowedOrigins)) {
            config.addAllowedOriginPattern("*");
            System.out.println("允许所有域名访问");
        } else {
            Arrays.stream(allowedOrigins.split(","))
                  .forEach(origin -> {
                      config.addAllowedOrigin(origin);
                      System.out.println("允许域名: " + origin);
                  });
        }
        
        // 设置允许的方法
        Arrays.stream(allowedMethods.split(","))
              .map(String::trim)
              .forEach(method -> {
                  config.addAllowedMethod(method);
                  System.out.println("允许方法: " + method);
              });
        
        // 设置允许的请求头
        if ("*".equals(allowedHeaders)) {
            config.addAllowedHeader("*");
            System.out.println("允许所有请求头");
        } else {
            Arrays.stream(allowedHeaders.split(","))
                  .map(String::trim)
                  .forEach(header -> {
                      config.addAllowedHeader(header);
                      System.out.println("允许请求头: " + header);
                  });
        }
        
        // 设置是否允许凭证
        config.setAllowCredentials(allowCredentials);
        
        // 设置预检请求的有效期
        config.setMaxAge(maxAge);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        System.out.println("CORS过滤器初始化完成");
        return new CorsFilter(source);
    }
}
