package com.example.schematenancy.configurations;


import com.example.schematenancy.utils.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer is an interface in Spring that allows you to customize the configuration
 * of the Spring MVC (Model-View-Controller) framework.
 * It provides several methods that you can override to configure various aspects of the MVC framework.
 * We need it here to intercept the incoming requests and get the tenantId.
 */
@RequiredArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor);
    }

}
